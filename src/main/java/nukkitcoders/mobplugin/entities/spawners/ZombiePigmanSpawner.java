package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.utils.Utils;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.entities.BaseEntity;
import cn.nukkit.block.Block;
import cn.nukkit.entity.mob.EntityZombiePigman;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;

public class ZombiePigmanSpawner extends AbstractEntitySpawner {

    public ZombiePigmanSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        final int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (blockId != Block.NETHERRACK) {
        } else if (pos.y > 127 || pos.y < 1) {
        } else {
            BaseEntity entity = this.spawnTask.createEntity("ZombiePigman", pos.add(0, 1, 0));
            if (Utils.rand(1, 20) == 1) {
                entity.setBaby(true);
            }
        }
    }

    @Override
    public int getEntityNetworkId() {
        return EntityZombiePigman.NETWORK_ID;
    }
}