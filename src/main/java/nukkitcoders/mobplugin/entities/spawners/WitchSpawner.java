package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.walking.Witch;
import nukkitcoders.mobplugin.utils.Utils;

public class WitchSpawner extends AbstractEntitySpawner {

    public WitchSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (Utils.rand(1, 5) != 1 && biomeId != 6 && biomeId != 134) {
            return;
        }

        if (blockLightLevel > 7) {
        } else if (blockId != Block.GRASS) {
        } else if (pos.y > 255 || pos.y < 1) {
        } else if (MobPlugin.isMobSpawningAllowedByTime(level)) {
            this.spawnTask.createEntity("Witch", pos.add(0, 1, 0));
        }
    }

    @Override
    public int getEntityNetworkId() {
        return Witch.NETWORK_ID;
    }
}
