package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.animal.flying.Parrot;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.utils.Utils;

public class ParrotSpawner extends AbstractEntitySpawner {

    public ParrotSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public void spawn(Player player, Position pos, Level level) {
        if (Utils.rand(1, 3) == 1) {
            return;
        }

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (biomeId != 21 && biomeId != 149 && biomeId != 23 && biomeId != 151) {
        } else if (blockId != Block.GRASS && blockId != Block.LEAVES) {
        } else if (pos.y > 255 || pos.y < 1) {
        } else if (MobPlugin.isAnimalSpawningAllowedByTime(level)) {
            this.spawnTask.createEntity("Parrot", pos.add(0, 1, 0));
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return Parrot.NETWORK_ID;
    }
}
