package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.passive.EntitySquid;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;

public class SquidSpawner extends AbstractEntitySpawner {

    public SquidSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public void spawn(Player player, Position pos, Level level) {
        final int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);
        final int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (blockId != Block.WATER && blockId != Block.STILL_WATER) {
        } else if (biomeId != 0) {
        } else if (pos.y > 255 || pos.y < 1) {
        } else if (MobPlugin.isAnimalSpawningAllowedByTime(level)) {
            int b = level.getBlockIdAt((int) pos.x, (int) (pos.y -1), (int) pos.z);
            if (b == Block.WATER || b == Block.STILL_WATER) {
                this.spawnTask.createEntity("Squid", pos.add(0, -1, 0));
            }
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return EntitySquid.NETWORK_ID;
    }
}
