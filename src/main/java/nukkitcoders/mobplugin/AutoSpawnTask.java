package nukkitcoders.mobplugin;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.CreatureSpawnEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.entities.animal.flying.Parrot;
import nukkitcoders.mobplugin.entities.animal.jumping.Rabbit;
import nukkitcoders.mobplugin.entities.animal.swimming.*;
import nukkitcoders.mobplugin.entities.animal.walking.*;
import nukkitcoders.mobplugin.entities.autospawn.IEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.flying.Blaze;
import nukkitcoders.mobplugin.entities.monster.flying.Ghast;
import nukkitcoders.mobplugin.entities.monster.jumping.MagmaCube;
import nukkitcoders.mobplugin.entities.monster.jumping.Slime;
import nukkitcoders.mobplugin.entities.monster.walking.*;
import nukkitcoders.mobplugin.entities.spawners.*;
import nukkitcoders.mobplugin.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoSpawnTask implements Runnable {

    private Map<Integer, Integer> maxSpawns = new HashMap<>();

    private List<IEntitySpawner> entitySpawners = new ArrayList<>();

    private Config pluginConfig;

    private MobPlugin plugin;

    public AutoSpawnTask(MobPlugin plugin) {
        this.pluginConfig = plugin.config.pluginConfig;
        this.plugin = plugin;

        prepareMaxSpawns();
        prepareSpawnerClasses();
    }

    @Override
    public void run() {
        if (!plugin.getServer().getOnlinePlayers().isEmpty()) {
            for (IEntitySpawner spawner : entitySpawners) {
                spawner.spawn();
            }
        }
    }

    private void prepareSpawnerClasses() {
        //entitySpawners.add(new BatSpawner(this));
        entitySpawners.add(new BlazeSpawner(this));
        entitySpawners.add(new ChickenSpawner(this));
        entitySpawners.add(new CodSpawner(this));
        entitySpawners.add(new CowSpawner(this));
        entitySpawners.add(new CreeperSpawner(this));
        //entitySpawners.add(new DolphinSpawner(this));
        //entitySpawners.add(new DonkeySpawner(this));
        entitySpawners.add(new EndermanSpawner(this));
        entitySpawners.add(new GhastSpawner(this));
        entitySpawners.add(new HorseSpawner(this));
        entitySpawners.add(new HuskSpawner(this));
        entitySpawners.add(new MagmaCubeSpawner(this));
        entitySpawners.add(new MooshroomSpawner(this));
        entitySpawners.add(new OcelotSpawner(this));
        entitySpawners.add(new ParrotSpawner(this));
        entitySpawners.add(new PigSpawner(this));
        entitySpawners.add(new PolarBearSpawner(this));
        entitySpawners.add(new PufferfishSpawner(this));
        entitySpawners.add(new RabbitSpawner(this));
        entitySpawners.add(new SalmonSpawner(this));
        entitySpawners.add(new SheepSpawner(this));
        entitySpawners.add(new SkeletonSpawner(this));
        entitySpawners.add(new SlimeSpawner(this));
        entitySpawners.add(new SpiderSpawner(this));
        entitySpawners.add(new StraySpawner(this));
        entitySpawners.add(new SquidSpawner(this));
        //entitySpawners.add(new TropicalFishSpawner(this));
        //entitySpawners.add(new TurtleSpawner(this));
        entitySpawners.add(new WitchSpawner(this));
        entitySpawners.add(new WitherSkeletonSpawner(this));
        entitySpawners.add(new WolfSpawner(this));
        entitySpawners.add(new ZombieSpawner(this));
        entitySpawners.add(new ZombiePigmanSpawner(this));
    }

    private void prepareMaxSpawns() {
        //maxSpawns.put(Bat.NETWORK_ID, this.pluginConfig.getInt("autospawn.bat"));
        maxSpawns.put(Blaze.NETWORK_ID, this.pluginConfig.getInt("autospawn.blaze"));
        maxSpawns.put(Chicken.NETWORK_ID, this.pluginConfig.getInt("autospawn.chicken"));
        maxSpawns.put(Cod.NETWORK_ID, this.pluginConfig.getInt("autospawn.cod"));
        maxSpawns.put(Cow.NETWORK_ID, this.pluginConfig.getInt("autospawn.cow"));
        maxSpawns.put(Creeper.NETWORK_ID, this.pluginConfig.getInt("autospawn.creeper"));
        //maxSpawns.put(Dolphin.NETWORK_ID, this.pluginConfig.getInt("autospawn.dolphin"));
        //maxSpawns.put(Donkey.NETWORK_ID, this.pluginConfig.getInt("autospawn.donkey"));
        maxSpawns.put(Enderman.NETWORK_ID, this.pluginConfig.getInt("autospawn.enderman"));
        maxSpawns.put(Ghast.NETWORK_ID, this.pluginConfig.getInt("autospawn.ghast"));
        maxSpawns.put(Horse.NETWORK_ID, this.pluginConfig.getInt("autospawn.horse"));
        maxSpawns.put(Husk.NETWORK_ID, this.pluginConfig.getInt("autospawn.husk"));
        maxSpawns.put(MagmaCube.NETWORK_ID, this.pluginConfig.getInt("autospawn.magmacube"));
        maxSpawns.put(Mooshroom.NETWORK_ID, this.pluginConfig.getInt("autospawn.mooshroom"));
        maxSpawns.put(Ocelot.NETWORK_ID, this.pluginConfig.getInt("autospawn.ocelot"));
        maxSpawns.put(Parrot.NETWORK_ID, this.pluginConfig.getInt("autospawn.parrot"));
        maxSpawns.put(Pig.NETWORK_ID, this.pluginConfig.getInt("autospawn.pig"));
        maxSpawns.put(PolarBear.NETWORK_ID, this.pluginConfig.getInt("autospawn.polarbear"));
        maxSpawns.put(Pufferfish.NETWORK_ID, this.pluginConfig.getInt("autospawn.pufferfish"));
        maxSpawns.put(Rabbit.NETWORK_ID, this.pluginConfig.getInt("autospawn.rabbit"));
        maxSpawns.put(Salmon.NETWORK_ID, this.pluginConfig.getInt("autospawn.salmon"));
        maxSpawns.put(Sheep.NETWORK_ID, this.pluginConfig.getInt("autospawn.sheep"));
        maxSpawns.put(Skeleton.NETWORK_ID, this.pluginConfig.getInt("autospawn.skeleton"));
        maxSpawns.put(Slime.NETWORK_ID, this.pluginConfig.getInt("autospawn.slime"));
        maxSpawns.put(Spider.NETWORK_ID, this.pluginConfig.getInt("autospawn.spider"));
        maxSpawns.put(Squid.NETWORK_ID, this.pluginConfig.getInt("autospawn.squid"));
        maxSpawns.put(Stray.NETWORK_ID, this.pluginConfig.getInt("autospawn.stray"));
        //maxSpawns.put(TropicalFish.NETWORK_ID, this.pluginConfig.getInt("autospawn.tropicalfish"));
        //maxSpawns.put(Turtle.NETWORK_ID, this.pluginConfig.getInt("autospawn.turtle"));
        maxSpawns.put(Witch.NETWORK_ID, this.pluginConfig.getInt("autospawn.witch"));
        maxSpawns.put(WitherSkeleton.NETWORK_ID, this.pluginConfig.getInt("autospawn.witherskeleton"));
        maxSpawns.put(Wolf.NETWORK_ID, this.pluginConfig.getInt("autospawn.wolf"));
        maxSpawns.put(Zombie.NETWORK_ID, this.pluginConfig.getInt("autospawn.zombie"));
        maxSpawns.put(ZombiePigman.NETWORK_ID, this.pluginConfig.getInt("autospawn.zombiepigman"));
    }

    public boolean entitySpawnAllowed(Level level, int networkId, Vector3 pos) {
        int count = 0;
        int max = maxSpawns.getOrDefault(networkId, 0);
        for (Entity entity : level.getEntities()) {
            if (entity.isAlive() && entity.getNetworkId() == networkId && new Vector3(pos.x, entity.y, pos.z).distanceSquared(entity) < 10000) {
                count++;
                if (count > max) {
                    return false;
                }
            }
        }

        return count < max;
    }

    public BaseEntity createEntity(Object type, Position pos) {
        BaseEntity entity = (BaseEntity) Entity.createEntity((String) type, pos);
        if (entity != null) {
            if (!entity.isInsideOfSolid() && !tooNearOfPlayer(pos)) {
                CreatureSpawnEvent ev = new CreatureSpawnEvent(entity.getNetworkId(), pos, entity.namedTag, CreatureSpawnEvent.SpawnReason.NATURAL);
                Server.getInstance().getPluginManager().callEvent(ev);
                if (!ev.isCancelled()) {
                    entity.spawnToAll();
                } else {
                    entity.close();
                }
            } else {
                entity.close();
            }
        }
        return entity;
    }

    private boolean tooNearOfPlayer(Position pos) {
        for (Player p : pos.getLevel().getPlayers().values()) {
            if (p.distanceSquared(pos) < 144) {
                return true;
            }
        }
        return false;
    }

    public int getRandomSafeXZCoord(int degree, int safeDegree, int correctionDegree) {
        int addX = Utils.rand((degree >> 1) * -1, degree >> 1);
        if (addX >= 0) {
            if (degree < safeDegree) {
                addX = safeDegree;
                addX += Utils.rand((correctionDegree >> 1) * -1, correctionDegree >> 1);
            }
        } else {
            if (degree > safeDegree) {
                addX = -safeDegree;
                addX += Utils.rand((correctionDegree >> 1) * -1, correctionDegree >> 1);
            }
        }

        return addX;
    }

    public int getSafeYCoord(Level level, Position pos) {
        int x = (int) pos.x;
        int y = (int) pos.y;
        int z = (int) pos.z;

        if (level.getBlockIdAt(x, y, z) == Block.AIR) {
            while (true) {
                y--;
                if (y > 127) {
                    y = 128;
                    break;
                }
                if (y < 1) {
                    y = 0;
                    break;
                }
                if (level.getBlockIdAt(x, y, z) != Block.AIR) {
                    int checkNeedDegree = 3;
                    int checkY = y;
                    while (true) {
                        checkY++;
                        checkNeedDegree--;
                        if (checkY > 127 || checkY < 1 || level.getBlockIdAt(x, checkY, z) != Block.AIR) {
                            break;
                        }

                        if (checkNeedDegree <= 0) {
                            return y;
                        }
                    }
                }
            }
        } else {
            while (true) {
                y++;
                if (y > 127) {
                    y = 128;
                    break;
                }

                if (y < 1) {
                    y = 0;
                    break;
                }

                if (level.getBlockIdAt(x, y, z) != Block.AIR) {
                    int checkNeedDegree = 3;
                    int checkY = y;
                    while (true) {
                        checkY--;
                        checkNeedDegree--;
                        if (checkY > 127 || checkY < 1 || level.getBlockIdAt(x, checkY, z) != Block.AIR) {
                            break;
                        }

                        if (checkNeedDegree <= 0) {
                            return y;
                        }
                    }
                }
            }
        }
        return y;
    }
}
