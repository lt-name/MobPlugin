package nukkitcoders.mobplugin;

public class Config {

    public cn.nukkit.utils.Config pluginConfig;

    public int spawnDelay;
    public int despawnTicks;
    public int spawnerRange;
    public boolean noXpOrbs;
    public boolean noSpawnEggWasting;
    public boolean killOnDespawn;

    Config(MobPlugin plugin) {
        plugin.saveDefaultConfig();
        pluginConfig = plugin.getConfig();
    }

    boolean init(MobPlugin plugin) {
        if (pluginConfig.getInt("config-version") != 11) {
            if (pluginConfig.getInt("config-version") == 10) {
                pluginConfig.set("other.kill-mobs-on-despawn", false);
            } else if (pluginConfig.getInt("config-version") == 9) {
                pluginConfig.set("other.spawn-no-spawning-area", 1);
                pluginConfig.set("other.kill-mobs-on-despawn", false);
            } else {
                plugin.getLogger().warning("MobPlugin's config file is outdated. Please delete the old config.");
                plugin.getLogger().error("Config error. Plugin will be disabled.");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return false;
            }

            pluginConfig.set("config-version", 11);
            pluginConfig.save();
            plugin.getLogger().notice("Config file updated to version 11.");
        }

        spawnDelay = pluginConfig.getInt("entities.autospawn-ticks");
        noXpOrbs = true;//pluginConfig.getBoolean("other.use-no-xp-orbs");
        noSpawnEggWasting = pluginConfig.getBoolean("other.do-not-waste-spawn-eggs");
        despawnTicks = pluginConfig.getInt("entities.despawn-ticks");
        spawnerRange = pluginConfig.getInt("other.spawner-spawn-range");
        killOnDespawn = false;//pluginConfig.getBoolean("other.kill-mobs-on-despawn");

        return true;
    }
}
