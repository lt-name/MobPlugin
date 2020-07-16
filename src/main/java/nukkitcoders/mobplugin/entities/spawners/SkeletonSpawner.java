package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.walking.Skeleton;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class SkeletonSpawner extends AbstractEntitySpawner {

    public SkeletonSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (Block.transparent[blockId]) {
        } else if (blockLightLevel > 7) {
        } else if (pos.y > 255 || pos.y < 1 || blockId == Block.AIR) {
        } else if (biomeId == 8) {
        } else if (MobPlugin.isMobSpawningAllowedByTime(level)) {
            this.spawnTask.createEntity("Skeleton", pos.add(0, 1, 0));
        }
    }

    @Override
    public int getEntityNetworkId() {
        return Skeleton.NETWORK_ID;
    }
}
