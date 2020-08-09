package nukkitcoders.mobplugin;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.PluginBase;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.entities.animal.flying.Bat;
import nukkitcoders.mobplugin.entities.animal.flying.Bee;
import nukkitcoders.mobplugin.entities.animal.flying.Parrot;
import nukkitcoders.mobplugin.entities.animal.jumping.Rabbit;
import nukkitcoders.mobplugin.entities.animal.swimming.*;
import nukkitcoders.mobplugin.entities.animal.walking.*;
import nukkitcoders.mobplugin.entities.block.BlockEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.flying.*;
import nukkitcoders.mobplugin.entities.monster.jumping.MagmaCube;
import nukkitcoders.mobplugin.entities.monster.jumping.Slime;
import nukkitcoders.mobplugin.entities.monster.swimming.ElderGuardian;
import nukkitcoders.mobplugin.entities.monster.swimming.Guardian;
import nukkitcoders.mobplugin.entities.monster.walking.*;
import nukkitcoders.mobplugin.entities.projectile.*;
import nukkitcoders.mobplugin.utils.Utils;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz (kniffo80)</a>
 */
public class MobPlugin extends PluginBase implements Listener {

    public Config config;

    private static MobPlugin instance;

    public static boolean throttle;

    public static MobPlugin getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        if (!this.getServer().getName().equals("Nukkit")) {
            this.getLogger().warning("MobPlugin does not support this software.");
            this.getLogger().error("Incompatible server software. The plugin will be disabled.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        config = new Config(this);

        if (!config.init(this)) {
            return;
        }

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        this.registerEntities();

        if (config.spawnDelay > 0) {
            this.getServer().getScheduler().scheduleDelayedRepeatingTask(this, new AutoSpawnTask(this), config.spawnDelay, config.spawnDelay);

            /*if (!this.getServer().getPropertyBoolean("spawn-animals") || !this.getServer().getPropertyBoolean("spawn-mobs")) {
                this.getServer().getLogger().notice("Disabling mob/animal spawning from server.properties does not disable spawning in MobPlugin");
            }*/
        }

        this.getServer().getScheduler().scheduleDelayedRepeatingTask(this, () -> {
            if (this.getServer().getOnlinePlayers().size() >= 30) {
                MobPlugin.throttle = true;
            } else {
                MobPlugin.throttle = false;
            }
        }, 1200, 1200, false);
    }

    @Override
    public void onDisable() {
        RouteFinderThreadPool.shutDownNow();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("summon")) {
            if (args.length == 0 || (args.length == 1 && !(sender instanceof Player))) {
                return false;
            }

            String mob = Character.toUpperCase(args[0].charAt(0)) + args[0].substring(1);
            int max = mob.length() - 1;
            for (int x = 2; x < max; x++) {
                if (mob.charAt(x) == '_') {
                    mob = mob.substring(0, x) + Character.toUpperCase(mob.charAt(x + 1)) + mob.substring(x + 2);
                }
            }

            Player playerThatSpawns;

            if (args.length == 2) {
                playerThatSpawns = getServer().getPlayer(args[1].replace("@s", sender.getName()));
            } else {
                playerThatSpawns = (Player) sender;
            }

            if (playerThatSpawns != null) {
                Position pos = playerThatSpawns.getPosition();
                Entity ent;
                if ((ent = Entity.createEntity(mob, pos)) != null) {
                    ent.spawnToAll();
                    sender.sendMessage("\u00A76Spawned " + mob + " to " + playerThatSpawns.getName());
                } else {
                    sender.sendMessage("\u00A7cUnable to spawn " + mob);
                }
            } else {
                sender.sendMessage("\u00A7cUnknown player " + (args.length == 2 ? args[1] : sender.getName()));
            }
        } else if (cmd.getName().equalsIgnoreCase("mob")) {
            if (args.length == 0) {
                sender.sendMessage("-- MobPlugin " + this.getDescription().getVersion() + " --");
                sender.sendMessage("/mob spawn <entity> <opt:player> - Summon entity");
                sender.sendMessage("/mob removeall - Remove all living mobs");
                sender.sendMessage("/mob removeitems - Remove all items from ground");
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "spawn":
                    if (args.length == 1 || (args.length == 2 && !(sender instanceof Player))) {
                        sender.sendMessage("Usage: /mob spawn <entity> <opt:player>");
                        break;
                    }

                    String mob = args[1];
                    Player playerThatSpawns;

                    if (args.length == 3) {
                        playerThatSpawns = this.getServer().getPlayer(args[2]);
                    } else {
                        playerThatSpawns = (Player) sender;
                    }

                    if (playerThatSpawns != null) {
                        Position pos = playerThatSpawns.getPosition();

                        Entity ent;
                        if ((ent = Entity.createEntity(mob, pos)) != null) {
                            ent.spawnToAll();
                            sender.sendMessage("Spawned " + mob + " to " + playerThatSpawns.getName());
                        } else {
                            sender.sendMessage("Unable to spawn " + mob);
                        }
                    } else {
                        sender.sendMessage("Unknown player " + (args.length == 3 ? args[2] : sender.getName()));
                    }
                    break;
                case "removeall":
                    int count = 0;
                    for (Level level : getServer().getLevels().values()) {
                        for (Entity entity : level.getEntities()) {
                            if (entity instanceof BaseEntity) {
                                entity.close();
                                ++count;
                            }
                        }
                    }
                    sender.sendMessage("Removed " + count + " entities from all levels.");
                    break;
                case "removeitems":
                    count = 0;
                    for (Level level : getServer().getLevels().values()) {
                        for (Entity entity : level.getEntities()) {
                            if (entity instanceof EntityItem && entity.isOnGround()) {
                                entity.close();
                                ++count;
                            }
                        }
                    }
                    sender.sendMessage("Removed " + count + " items on ground from all levels.");
                    break;
                default:
                    sender.sendMessage("Unknown command.");
                    break;
            }
        }

        return true;
    }

    private void registerEntities() {
        BlockEntity.registerBlockEntity("MobSpawner", BlockEntitySpawner.class);

        Entity.registerEntity(Bat.class.getSimpleName(), Bat.class);
        Entity.registerEntity(Bee.class.getSimpleName(), Bee.class);
        Entity.registerEntity(Cat.class.getSimpleName(), Cat.class);
        Entity.registerEntity(Chicken.class.getSimpleName(), Chicken.class);
        Entity.registerEntity(Cod.class.getSimpleName(), Cod.class);
        Entity.registerEntity(Cow.class.getSimpleName(), Cow.class);
        Entity.registerEntity(Dolphin.class.getSimpleName(), Dolphin.class);
        Entity.registerEntity(Donkey.class.getSimpleName(), Donkey.class);
        Entity.registerEntity(Fox.class.getSimpleName(), Fox.class);
        Entity.registerEntity(Horse.class.getSimpleName(), Horse.class);
        Entity.registerEntity(MagmaCube.class.getSimpleName(), MagmaCube.class);
        Entity.registerEntity(Llama.class.getSimpleName(), Llama.class);
        Entity.registerEntity(Mooshroom.class.getSimpleName(), Mooshroom.class);
        Entity.registerEntity(Mule.class.getSimpleName(), Mule.class);
        Entity.registerEntity(Ocelot.class.getSimpleName(), Ocelot.class);
        Entity.registerEntity(Panda.class.getSimpleName(), Panda.class);
        Entity.registerEntity(Parrot.class.getSimpleName(), Parrot.class);
        Entity.registerEntity(Pig.class.getSimpleName(), Pig.class);
        Entity.registerEntity(PolarBear.class.getSimpleName(), PolarBear.class);
        Entity.registerEntity(Pufferfish.class.getSimpleName(), Pufferfish.class);
        Entity.registerEntity(Rabbit.class.getSimpleName(), Rabbit.class);
        Entity.registerEntity(Salmon.class.getSimpleName(), Salmon.class);
        Entity.registerEntity(SkeletonHorse.class.getSimpleName(), SkeletonHorse.class);
        Entity.registerEntity(Sheep.class.getSimpleName(), Sheep.class);
        Entity.registerEntity(Squid.class.getSimpleName(), Squid.class);
        Entity.registerEntity(TropicalFish.class.getSimpleName(), TropicalFish.class);
        Entity.registerEntity(Turtle.class.getSimpleName(), Turtle.class);
        Entity.registerEntity(Villager.class.getSimpleName(), Villager.class);
        Entity.registerEntity(VillagerV2.class.getSimpleName(), VillagerV2.class);
        Entity.registerEntity(ZombieHorse.class.getSimpleName(), ZombieHorse.class);
        Entity.registerEntity(WanderingTrader.class.getSimpleName(), WanderingTrader.class);
        Entity.registerEntity(Strider.class.getSimpleName(), Strider.class);

        Entity.registerEntity(Blaze.class.getSimpleName(), Blaze.class);
        Entity.registerEntity(Ghast.class.getSimpleName(), Ghast.class);
        Entity.registerEntity(CaveSpider.class.getSimpleName(), CaveSpider.class);
        Entity.registerEntity(WitherSkeleton.class.getSimpleName(), WitherSkeleton.class);
        Entity.registerEntity(Creeper.class.getSimpleName(), Creeper.class);
        Entity.registerEntity(Drowned.class.getSimpleName(), Drowned.class);
        Entity.registerEntity(ElderGuardian.class.getSimpleName(), ElderGuardian.class);
        Entity.registerEntity(EnderDragon.class.getSimpleName(), EnderDragon.class);
        Entity.registerEntity(Enderman.class.getSimpleName(), Enderman.class);
        Entity.registerEntity(Endermite.class.getSimpleName(), Endermite.class);
        Entity.registerEntity(Evoker.class.getSimpleName(), Evoker.class);
        Entity.registerEntity(Guardian.class.getSimpleName(), Guardian.class);
        Entity.registerEntity(Husk.class.getSimpleName(), Husk.class);
        Entity.registerEntity(IronGolem.class.getSimpleName(), IronGolem.class);
        Entity.registerEntity(Phantom.class.getSimpleName(), Phantom.class);
        Entity.registerEntity(ZombiePigman.class.getSimpleName(), ZombiePigman.class);
        Entity.registerEntity(Shulker.class.getSimpleName(), Shulker.class);
        Entity.registerEntity(Silverfish.class.getSimpleName(), Silverfish.class);
        Entity.registerEntity(Skeleton.class.getSimpleName(), Skeleton.class);
        Entity.registerEntity(Slime.class.getSimpleName(), Slime.class);
        Entity.registerEntity(SnowGolem.class.getSimpleName(), SnowGolem.class);
        Entity.registerEntity(Spider.class.getSimpleName(), Spider.class);
        Entity.registerEntity(Stray.class.getSimpleName(), Stray.class);
        Entity.registerEntity(Vex.class.getSimpleName(), Vex.class);
        Entity.registerEntity(Vindicator.class.getSimpleName(), Vindicator.class);
        Entity.registerEntity(Witch.class.getSimpleName(), Witch.class);
        Entity.registerEntity(Wither.class.getSimpleName(), Wither.class);
        Entity.registerEntity(Wolf.class.getSimpleName(), Wolf.class);
        Entity.registerEntity(Zombie.class.getSimpleName(), Zombie.class);
        Entity.registerEntity(ZombieVillager.class.getSimpleName(), ZombieVillager.class);
        Entity.registerEntity(ZombieVillagerV2.class.getSimpleName(), ZombieVillagerV2.class);
        Entity.registerEntity(Pillager.class.getSimpleName(), Pillager.class);
        Entity.registerEntity(Ravager.class.getSimpleName(), Ravager.class);
        Entity.registerEntity(Hoglin.class.getSimpleName(), Hoglin.class);
        Entity.registerEntity(Piglin.class.getSimpleName(), Piglin.class);
        Entity.registerEntity(Zoglin.class.getSimpleName(), Zoglin.class);

        Entity.registerEntity("BlueWitherSkull", EntityBlueWitherSkull.class);
        Entity.registerEntity("BlazeFireBall", EntityBlazeFireBall.class);
        Entity.registerEntity("GhastFireBall", EntityGhastFireBall.class);
        Entity.registerEntity("ShulkerBullet", EntityShulkerBullet.class);
        Entity.registerEntity("EnderCharge", EntityEnderCharge.class);
        Entity.registerEntity("WitherSkull", EntityWitherSkull.class);
    }

    public static boolean isAnimalSpawningAllowedByTime(Level level) {
        int time = level.getTime() % Level.TIME_FULL;
        return time < 13184 || time > 22800;
    }

    public static boolean isMobSpawningAllowedByTime(Level level) {
        int time = level.getTime() % Level.TIME_FULL;
        return time > 13184 && time < 22800;
    }

    public static boolean shouldMobBurn(Level level, BaseEntity entity) {
        int time = level.getTime() % Level.TIME_FULL;
        return !entity.isOnFire() && !level.isRaining() && !entity.isBaby() && (time < 12567 || time > 23450) && !Utils.entityInsideWaterFast(entity) && level.canBlockSeeSky(entity);
    }
}
