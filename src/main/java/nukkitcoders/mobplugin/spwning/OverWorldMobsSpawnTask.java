package nukkitcoders.mobplugin.spwning;

import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.format.FullChunk;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.animal.jumping.Rabbit;
import nukkitcoders.mobplugin.entities.animal.walking.*;
import nukkitcoders.mobplugin.entities.monster.walking.*;
import nukkitcoders.mobplugin.utils.Utils;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class OverWorldMobsSpawnTask implements Runnable {

    private static ThreadLocal<HashSet<FullChunk>> spawnChunks = ThreadLocal.withInitial(HashSet::new);

    @SuppressWarnings("unchecked")
    private static Class<? extends Entity>[]
            animals = new Class[] {
                Rabbit.class,
                Chicken.class,
                Cow.class,
                Chicken.class,
                Cow.class,
                Chicken.class,
                Cow.class,
                Horse.class,
                Pig.class,
                Sheep.class,
                Pig.class,
                Sheep.class,
                Pig.class,
                Sheep.class
    },
            monsters_ow = new Class[] {
                Creeper.class,
                Enderman.class,
                Skeleton.class,
                Zombie.class,
                ZombieVillager.class,
                Spider.class
    };

    @Override
    public void run() {
        Server server = Server.getInstance();
        HashSet<FullChunk> chunks = spawnChunks.get();
        server.getLevels().values().forEach(level -> {
            if (level.getDimension() != 0) return; // Block if is not overworld
            AtomicBoolean ow_day = new AtomicBoolean(false);
            Class<? extends Entity>[] arr = null;
            int time = level.getTime() % Level.TIME_FULL;
            if (time > 13184 && time < 22800) {
                arr = monsters_ow;
            } else {
                ow_day.set(true);
                arr = animals;
            }
            final Class<? extends Entity>[] a = arr;

            chunks.addAll(level.getChunks().values());
            chunks.forEach(chunk -> {
                if (ow_day.get() && ThreadLocalRandom.current().nextInt(20) != 0) {
                    return;
                }
                CHUNK:
                if (chunk.getEntities().size() < 5 && Utils.rand(0, 200) == 0) {
                    Class<? extends Entity> clazz = a[Utils.rand(0, a.length)];
                    int xPos = Utils.rand(0, 15) | (chunk.getX() << 4);
                    int zPos = Utils.rand(0, 15) | (chunk.getZ() << 4);
                    int yPos;
                    yPos = level.getHighestBlockAt(xPos, zPos);
                    if (RandomSpawn.isUnafe(chunk.getBlockId(xPos & 0xF, yPos, zPos & 0xF))) {
                        break CHUNK;
                    }/* else if (ow_day.get() && chunk.getBlockId(xPos & 0xF, yPos - 1, zPos & 0xF) != Block.GRASS) {
                            break CHUNK;
                        }*/
                    int rand = Utils.rand(1, 5); // min / max count
                    for (int i = 1; i < rand; i++) {
                        Entity entity = MobPlugin.create(clazz.getSimpleName(), new Location(xPos, yPos, zPos, level));
                        level.addEntity(entity);
                        entity.spawnToAll();
                    }
                }
            });
        });
        chunks.clear();
    }
}
