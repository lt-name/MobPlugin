package nukkitcoders.mobplugin.entities.monster.walking;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntitySmite;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.event.entity.EntityShootBowEvent;
import cn.nukkit.entity.projectile.EntityArrow;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBow;
import cn.nukkit.level.Location;
import cn.nukkit.level.Sound;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.monster.WalkingMonster;
import nukkitcoders.mobplugin.route.WalkerRouteFinder;
import nukkitcoders.mobplugin.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Stray extends WalkingMonster implements EntitySmite {

    public static final int NETWORK_ID = 46;

    public Stray(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.route = new WalkerRouteFinder(this);
    }

    @Override
    public void initEntity() {
        super.initEntity();

        this.setMaxHealth(20);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.99f;
    }

    @Override
    public void spawnTo(Player player) {
        super.spawnTo(player);

        MobEquipmentPacket pk = new MobEquipmentPacket();
        pk.eid = this.getId();
        pk.item = new ItemBow();
        pk.hotbarSlot = 0;
        player.dataPacket(pk);
    }

    @Override
    public boolean entityBaseTick(int tickDiff) {
        if (getServer().getDifficulty() == 0) {
            this.close();
            return true;
        }

        boolean hasUpdate  = super.entityBaseTick(tickDiff);

        if (MobPlugin.shouldMobBurn(level, this)) {
            this.setOnFire(100);
        }

        return hasUpdate;
    }

    public void attackEntity(Entity player) {
        if (this.attackDelay > 23 && Utils.rand(1, 32) < 4 && this.distanceSquared(player) <= 55) {
            this.attackDelay = 0;

            double f = 1.3;
            double yaw = this.yaw + Utils.rand(-12.0, 12.0);
            double pitch = this.pitch + Utils.rand(-7.0, 7.0);
            Location pos = new Location(this.x - Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * 0.5, this.y + this.getHeight() - 0.18,
                    this.z + Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * 0.5, yaw, pitch, this.level);
            if (this.getLevel().getBlockIdAt((int) pos.getX(),(int) pos.getY(),(int) pos.getZ()) == Block.AIR) {
                Entity k = Entity.createEntity("Arrow", pos, this);
                if (!(k instanceof EntityArrow)) {
                    return;
                }

                EntityArrow arrow = (EntityArrow) k;
                arrow.setMotion(new Vector3(-Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * f * f, -Math.sin(Math.toRadians(pitch)) * f * f,
                        Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * f * f));

                EntityShootBowEvent ev = new EntityShootBowEvent(this, Item.get(Item.ARROW, 0, 1), arrow, f);
                this.server.getPluginManager().callEvent(ev);

                EntityProjectile projectile = ev.getProjectile();
                if (ev.isCancelled()) {
                    if (this.stayTime > 0 || this.distance(this.target) <= ((this.getWidth()) / 2 + 0.05) * nearbyDistanceMultiplier()) projectile.close();
                } else {
                    ProjectileLaunchEvent launch = new ProjectileLaunchEvent(projectile);
                    this.server.getPluginManager().callEvent(launch);
                    if (launch.isCancelled()) {
                        if (this.stayTime > 0 || this.distance(this.target) <= ((this.getWidth()) / 2 + 0.05) * nearbyDistanceMultiplier()) projectile.close();
                    } else {
                        projectile.spawnToAll();
                        ((EntityArrow) projectile).setPickupMode(EntityArrow.PICKUP_NONE);
                        this.level.addSound(this, Sound.RANDOM_BOW);
                    }
                }
            }
        }
    }

    @Override
    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();

        for (int i = 0; i < Utils.rand(0, 2); i++) {
            drops.add(Item.get(Item.BONE, 0, 1));
        }

        for (int i = 0; i < Utils.rand(0, 2); i++) {
            drops.add(Item.get(Item.ARROW, 0, 1));
        }

        return drops.toArray(new Item[0]);
    }

    @Override
    public int getKillExperience() {
        return 5;
    }

    @Override
    public int nearbyDistanceMultiplier() {
        return 10;
    }
}
