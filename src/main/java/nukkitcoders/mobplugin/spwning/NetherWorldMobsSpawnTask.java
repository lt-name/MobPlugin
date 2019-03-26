package nukkitcoders.mobplugin.spwning;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;
import cn.nukkit.level.format.FullChunk;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.entities.monster.flying.Blaze;
import nukkitcoders.mobplugin.entities.monster.flying.Ghast;
import nukkitcoders.mobplugin.entities.monster.walking.*;
import nukkitcoders.mobplugin.utils.Utils;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetherWorldMobsSpawnTask implements Runnable {

    private static ThreadLocal<HashSet<FullChunk>> spawnChunks = ThreadLocal.withInitial(HashSet::new);

    @SuppressWarnings("unchecked")
    private static Class<? extends Entity>[]
        monsters_nether = new Class[] {
            Blaze.class,
            Ghast.class,
            ZombiePigman.class,
            ZombiePigman.class,
            ZombiePigman.class,
            ZombiePigman.class,
            ZombiePigman.class,
            ZombiePigman.class
    };

    @Override
    public void run() {
        Server server = Server.getInstance();
        HashSet<FullChunk> chunks = spawnChunks.get();
        server.getLevels().values().forEach(level -> {
            if (level.getDimension() != 1) return; // Block if is not nether
            AtomicBoolean ow_day = new AtomicBoolean(false);
            Class<? extends Entity>[] arr = null;
            arr = monsters_nether;
            final Class<? extends Entity>[] a = arr;

            chunks.addAll(level.getChunks().values());
            chunks.forEach(chunk -> {
                if (ow_day.get() && ThreadLocalRandom.current().nextInt(20) != 0) {
                    return;
                }
                if (chunk.getEntities().size() < 5 && Utils.rand(0, 200) == 0) {
                    Class<? extends Entity> clazz = a[Utils.rand(0, a.length)];
                    int xPos = Utils.rand(0, 15) | (chunk.getX() << 4);
                    int zPos = Utils.rand(0, 15) | (chunk.getZ() << 4);
                    int yPos = 0;
                    int y = 126;
                    int relX = xPos & 0xF;
                    int relZ = zPos & 0xF;
                    for (; y > 2; y--) {
                        if (chunk.getBlockId(relX, y + 1, relZ) == Block.AIR
                                && chunk.getBlockId(relX, y, relZ) == Block.AIR
                                && !RandomSpawn.isUnafe(chunk.getBlockId(relX, y - 1, relZ))) {
                            yPos = y;
                        }
                    }
                    int rand = Utils.rand(1, 5); // min / max count
                    for (int i = 1; i < rand; i++) {
                        Entity entity = MobPlugin.create(clazz.getSimpleName(), new Location(xPos, yPos, zPos, level));
                        level.addEntity(entity);
                        entity.spawnToAll();
                    }
                }
            });
            chunks.clear();
        });
    }
}