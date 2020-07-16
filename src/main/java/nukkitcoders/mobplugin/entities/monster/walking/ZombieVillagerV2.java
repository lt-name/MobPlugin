package nukkitcoders.mobplugin.entities.monster.walking;

import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

public class ZombieVillagerV2 extends ZombieVillager {

    public static final int NETWORK_ID = 116;

    public ZombieVillagerV2(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }
}
