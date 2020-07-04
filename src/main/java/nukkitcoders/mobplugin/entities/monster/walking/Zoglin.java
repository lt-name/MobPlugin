package nukkitcoders.mobplugin.entities.monster.walking;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import nukkitcoders.mobplugin.entities.monster.WalkingMonster;
import nukkitcoders.mobplugin.utils.Utils;

public class Zoglin extends WalkingMonster {

    public final static int NETWORK_ID = 126;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public Zoglin(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getKillExperience() {
        return this.isBaby() ? 1 : Utils.rand(1, 3);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.setMaxHealth(40);
    }

    @Override
    public float getWidth() {
        return 0.9f;
    }

    @Override
    public float getHeight() {
        return 0.9f;
    }

    @Override
    public void attackEntity(Entity player) {
        //TODO
    }
}
