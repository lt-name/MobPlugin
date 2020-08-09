package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.walking.Enderman;
import nukkitcoders.mobplugin.utils.Utils;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class EndermanSpawner extends AbstractEntitySpawner {

    public EndermanSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public void spawn(Player player, Position pos, Level level) {
        if (Utils.rand(1, level.getDimension() == Level.DIMENSION_NETHER ? 10 : 7) != 1 && level.getDimension() != Level.DIMENSION_THE_END) {
            return;
        }

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (Block.transparent[blockId]) {
        } else if (blockLightLevel > 7 && level.getDimension() != Level.DIMENSION_NETHER && level.getDimension() != Level.DIMENSION_THE_END) {
        } else if ((pos.y > 255 || (level.getDimension() == Level.DIMENSION_NETHER && pos.y > 127)) || pos.y < 1 || blockId == Block.AIR) {
        } else if (MobPlugin.isMobSpawningAllowedByTime(level) || level.getDimension() == Level.DIMENSION_NETHER || level.getDimension() == Level.DIMENSION_THE_END) {
            this.spawnTask.createEntity("Enderman", pos.add(0, 1, 0));
        }
    }

    @Override
    public int getEntityNetworkId() {
        return Enderman.NETWORK_ID;
    }
}
