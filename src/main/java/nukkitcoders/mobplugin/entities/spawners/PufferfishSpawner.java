package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.passive.EntityPufferfish;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;

public class PufferfishSpawner extends AbstractEntitySpawner {

    public PufferfishSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public void spawn(Player player, Position pos, Level level) {
        final int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);
        final int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (blockId != Block.WATER && blockId != Block.STILL_WATER) {
        } else if (biomeId != 0 && biomeId != 7) {
        } else if (pos.y > 255 || pos.y < 1) {
        } else if (level.getName().equals("nether") || level.getName().equals("the_end")) {
        } else {
            int b = level.getBlockIdAt((int) pos.x, (int) (pos.y -1), (int) pos.z);
            if (b == Block.WATER || b == Block.STILL_WATER) {
                this.spawnTask.createEntity("Pufferfish", pos.add(0, -1, 0));
            }
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return EntityPufferfish.NETWORK_ID;
    }
}
