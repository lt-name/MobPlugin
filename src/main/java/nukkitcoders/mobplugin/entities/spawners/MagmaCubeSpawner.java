package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.jumping.MagmaCube;

public class MagmaCubeSpawner extends AbstractEntitySpawner {

    public MagmaCubeSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (blockId != Block.NETHERRACK) {
        } else if (pos.y > 127 || pos.y < 1) {
        } else {
            this.spawnTask.createEntity("MagmaCube", pos.add(0, 1, 0));
        }
    }

    @Override
    public final int getEntityNetworkId() {
        return MagmaCube.NETWORK_ID;
    }
}
