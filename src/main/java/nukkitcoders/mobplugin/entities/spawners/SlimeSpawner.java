package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.jumping.Slime;

public class SlimeSpawner extends AbstractEntitySpawner {

    public SlimeSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (blockId != Block.GRASS) {
        } else if (biomeId != 6 && biomeId != 134) {
        } else if (pos.y > 70 || pos.y < 1) {
        } else if (level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z) > 7) {
        } else if (MobPlugin.isMobSpawningAllowedByTime(level)) {
            this.spawnTask.createEntity("Slime", pos.add(0, 1, 0));
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return Slime.NETWORK_ID;
    }
}
