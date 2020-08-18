package nukkitcoders.mobplugin.entities.spawners;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.BaseEntity;
import nukkitcoders.mobplugin.entities.animal.flying.Bee;
import nukkitcoders.mobplugin.entities.autospawn.AbstractEntitySpawner;
import nukkitcoders.mobplugin.utils.Utils;

/**
 * @author lt_name
 */
public class BeeSpawner extends AbstractEntitySpawner {

    public BeeSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    @Override
    public void spawn(Player player, Position pos, Level level) {
        final int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);
        switch (biomeId) {
            case 1: //平原 Plains
            case 4: //森林 Forest
            case 27: //桦木森林 Birch Forest
            case 129: //向日葵平原 Sunflower Plains
            case 132: //繁华森林 Flower Forest
            case 155: //高大桦木森林 Tall Birch Forest
                /*//TODO improve this (This is only temporary)
                for (int y = -5; y < pos.y + 5; y++) {
                    for (int x = -5; x < pos.x + 5; x++) {
                        for (int z = -5; z < pos.z + 5; z++) {
                            if (pos.getLevel().getBlock(x, y, z).getId() == BlockID.BEE_NEST) {

                            }
                        }
                    }
                }*/
                if (pos.y > 255 || pos.y < 1) {
                }else if (MobPlugin.isAnimalSpawningAllowedByTime(level)) {
                    BaseEntity entity = this.spawnTask.createEntity("Bee", pos.add(0, 1, 0));
                }
                break;
        }


    }

    @Override
    public int getEntityNetworkId() {
        return Bee.NETWORK_ID;
    }

}
