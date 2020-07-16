package nukkitcoders.mobplugin.entities.autospawn;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import nukkitcoders.mobplugin.AutoSpawnTask;
import nukkitcoders.mobplugin.MobPlugin;
import nukkitcoders.mobplugin.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public abstract class AbstractEntitySpawner implements IEntitySpawner {

    protected AutoSpawnTask spawnTask;

    protected Server server;

    private List<String> disabledSpawnWorlds = new ArrayList<>();

    private int spawnAreaSize;

    public AbstractEntitySpawner(AutoSpawnTask spawnTask) {
        this.spawnTask = spawnTask;
        this.server = Server.getInstance();
        this.spawnAreaSize = MobPlugin.getInstance().config.pluginConfig.getInt("other.spawn-no-spawning-area");
        String disabledWorlds = MobPlugin.getInstance().config.pluginConfig.getString("entities.worlds-spawning-disabled");
        if (disabledWorlds != null && !disabledWorlds.trim().isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(disabledWorlds, ", ");
            while (tokenizer.hasMoreTokens()) {
                disabledSpawnWorlds.add(tokenizer.nextToken());
            }
        }
    }

    @Override
    public void spawn() {
        for (Player player : server.getOnlinePlayers().values()) {
            if (isWorldSpawnAllowed(player.getLevel())) {
                if (isSpawnAllowedByDifficulty()) {
                    spawnTo(player);
                }
            }
        }
    }

    private boolean isWorldSpawnAllowed(Level level) {
        for (String worldName : this.disabledSpawnWorlds) {
            if (level.getName().equalsIgnoreCase(worldName)) {
                return false;
            }
        }

        return level.getGameRules().getBoolean(GameRule.DO_MOB_SPAWNING);
    }

    private void spawnTo(Player player) {
        Position pos = player.getPosition();
        Level level = player.getLevel();

        if (this.spawnTask.entitySpawnAllowed(level, getEntityNetworkId(), player)) {
            if (pos != null) {
                pos.x += this.spawnTask.getRandomSafeXZCoord(50, 26, 6);
                pos.z += this.spawnTask.getRandomSafeXZCoord(50, 26, 6);
                pos.y = this.spawnTask.getSafeYCoord(level, pos);

                if (this.spawnAreaSize > 0 && level.getSpawnLocation().distance(pos) < this.spawnAreaSize) {
                    return;
                }
            } else {
                return;
            }
        } else {
            return;
        }

        spawn(player, pos, level);
    }

    private boolean isSpawnAllowedByDifficulty() {
        int randomNumber = Utils.rand(0, 3);

        switch (this.server.getDifficulty()) {
            case 0:
                return randomNumber == 0;
            case 1:
                return randomNumber <= 1;
            case 2:
                return randomNumber <= 2;
            default:
                return true;
        }
    }
}
