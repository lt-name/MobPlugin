package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.walking.WitherSkeleton;

public class WitherSkeletonSpawner extends AbstractEntitySpawner {

    public WitherSkeletonSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (pos.y > 127 || pos.y < 1 || blockId == Block.AIR) {
        } else if (blockId != Block.NETHERRACK) {
        } else {
            this.spawnTask.createEntity("WitherSkeleton", pos.add(0, 1, 0));
        }
    }

    @Override
    public int getEntityNetworkId() {
        return WitherSkeleton.NETWORK_ID;
    }
}
