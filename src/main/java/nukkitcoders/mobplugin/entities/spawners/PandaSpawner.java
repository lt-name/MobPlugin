package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.animal.walking.Panda;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.utils.Utils;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.AutoSpawnTask;

public class PandaSpawner extends AbstractEntitySpawner {

    public PandaSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public void spawn(Player player, Position pos, Level level) {
        final int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);
        if ((biomeId == 21 && Utils.rand(1, 10) != 1) || biomeId != 168 && biomeId != 169 && biomeId != 21) {
            return;
        }

        if (pos.y > 255 || pos.y < 1 || !MobPlugin.isAnimalSpawningAllowedByTime(level) ||
                level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) != Block.GRASS) {
            return;
        }

        BaseEntity entity = this.spawnTask.createEntity("Panda", pos.add(0, 1, 0));
        if (Utils.rand(1, 20) == 1) {
            entity.setBaby(true);
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return Panda.NETWORK_ID;
    }
}
