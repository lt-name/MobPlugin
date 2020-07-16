package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.walking.Wolf;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.utils.Utils;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class WolfSpawner extends AbstractEntitySpawner {

    public WolfSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (biomeId != 4 && biomeId != 5 && biomeId != 20 && biomeId != 27 && biomeId != 30 && biomeId != 32 && biomeId != 133 && biomeId != 158 ) {
        } else if (pos.y > 255 || pos.y < 1 || blockId == Block.AIR) {
        } else if (Block.transparent[blockId]) {
        } else if (MobPlugin.isAnimalSpawningAllowedByTime(level)) {
            BaseEntity entity = this.spawnTask.createEntity("Wolf", pos.add(0, 1, 0));
            if (Utils.rand(1, 20) == 1) {
                entity.setBaby(true);
            }
        }
    }

    @Override
    public int getEntityNetworkId() {
        return Wolf.NETWORK_ID;
    }
}
