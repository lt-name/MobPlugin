package nukkitcoders.mobplugin.entities.monster.walking;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import nukkitcoders.mobplugin.entities.monster.WalkingMonster;

public class Piglin extends WalkingMonster {

    public final static int NETWORK_ID = 123;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public Piglin(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getKillExperience() {
        return 0;
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.setMaxHealth(16);
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.95f;
    }

    @Override
    public void attackEntity(Entity player) {
        //TODO
    }
}
