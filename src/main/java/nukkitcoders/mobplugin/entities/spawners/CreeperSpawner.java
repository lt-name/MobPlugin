package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.walking.Creeper;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class CreeperSpawner extends AbstractEntitySpawner {

    public CreeperSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (!Block.solid[blockId]) {
        } else if (blockLightLevel > 7) {
        } else if (biomeId == 8) {
        } else if (pos.y > 255 || pos.y < 1 || blockId == Block.AIR) {
        } else if (MobPlugin.isMobSpawningAllowedByTime(level)) {
            this.spawnTask.createEntity("Creeper", pos.add(0, 1, 0));
        }
    }

    @Override
    public int getEntityNetworkId() {
        return Creeper.NETWORK_ID;
    }
}
