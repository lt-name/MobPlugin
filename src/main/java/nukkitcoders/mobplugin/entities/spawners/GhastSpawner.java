package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.entities.monster.flying.Ghast;

/**
 * @author PikyCZ
 */
public class GhastSpawner extends AbstractEntitySpawner {

    public GhastSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (Block.transparent[blockId]) {
        } else if (pos.y > 127 || pos.y < 1 || blockId == Block.AIR) {
        } else {
            this.spawnTask.createEntity("Ghast", pos.add(0, 1, 0));
        }
    }

    @Override
    public int getEntityNetworkId() {
        return Ghast.NETWORK_ID;
    }
}
