package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.walking.Husk;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.utils.Utils;

/**
 * @author PikyCZ
 */
public class HuskSpawner extends AbstractEntitySpawner {

    public HuskSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (blockLightLevel > 7) {
        } else if (pos.y > 255 || pos.y < 1 || blockId == Block.AIR) {
        } else if (biomeId != 2) {
        } else if (MobPlugin.isMobSpawningAllowedByTime(level)) {
            BaseEntity entity = this.spawnTask.createEntity("Husk", pos.add(0, 1, 0));
            if (Utils.rand(1, 20) == 1) {
                entity.setBaby(true);
            }
        }
    }

    @Override
    public int getEntityNetworkId() {
        return Husk.NETWORK_ID;
    }
}
