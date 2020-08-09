package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.animal.flying.Bat;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class BatSpawner extends AbstractEntitySpawner {

    public BatSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (Block.transparent[blockId]) {
        } else if (blockLightLevel > 3) {
        } else if (pos.y > 255 || pos.y < 1 || blockId == Block.AIR) {
        } else if (level.canBlockSeeSky(pos)) {
        } else if (MobPlugin.isAnimalSpawningAllowedByTime(level)) {
            this.spawnTask.createEntity("Bat", pos.add(0, 1, 0));
        }
    }

    @Override
    public int getEntityNetworkId() {
        return Bat.NETWORK_ID;
    }
}
