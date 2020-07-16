package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.animal.walking.Donkey;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.utils.Utils;

public class DonkeySpawner extends AbstractEntitySpawner {

    public DonkeySpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (blockId != Block.GRASS) {
        } else if (biomeId != 1 && biomeId != 35 && biomeId != 128 && biomeId != 129) {
        } else if (pos.y > 255 || pos.y < 1) {
        } else if (MobPlugin.isAnimalSpawningAllowedByTime(level)) {
            BaseEntity entity = this.spawnTask.createEntity("Donkey", pos.add(0, 1, 0));
            if (Utils.rand(1, 20) == 1) {
                entity.setBaby(true);
            }
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return Donkey.NETWORK_ID;
    }
}
