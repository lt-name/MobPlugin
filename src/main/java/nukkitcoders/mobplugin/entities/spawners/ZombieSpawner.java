package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.walking.Zombie;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.utils.Utils;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class ZombieSpawner extends AbstractEntitySpawner {

    public ZombieSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (blockLightLevel > 7) {
        } else if (biomeId == 8) {
        } else if (pos.y > 255 || pos.y < 1 || blockId == Block.AIR) {
        } else if (Block.transparent[blockId]) {
        } else if (MobPlugin.isMobSpawningAllowedByTime(level)) {
            if (Utils.rand(1, 40) == 30) {
                BaseEntity entity = this.spawnTask.createEntity("ZombieVillager", pos.add(0, 1, 0));
                if (Utils.rand(1, 20) == 1) {
                    entity.setBaby(true);
                }
            } else {
                BaseEntity entity = this.spawnTask.createEntity("Zombie", pos.add(0, 1, 0));
                if (Utils.rand(1, 20) == 1) {
                    entity.setBaby(true);
                }
            }
        }
    }

    @Override
    public int getEntityNetworkId() {
        return Zombie.NETWORK_ID;
    }
}
