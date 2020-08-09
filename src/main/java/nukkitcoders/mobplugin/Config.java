package nukkitcoders.mobplugin;

public class Config {

    public cn.nukkit.utils.Config pluginConfig;

    public int spawnDelay;
    public int despawnTicks;
    public int spawnerRange;
    public int endEndermanSpawnRate;
    public boolean noXpOrbs;
    public boolean noSpawnEggWasting;
    public boolean killOnDespawn;
    public boolean spawnersEnabled;

    Config(MobPlugin plugin) {
        plugin.saveDefaultConfig();
        pluginConfig = plugin.getConfig();
    }

    boolean init(MobPlugin plugin) {
        int ver = 13;

        if (pluginConfig.getInt("config-version") != ver) {
            if (pluginConfig.getInt("config-version") == 12) {
                pluginConfig.set("autospawn.fox", 0);
                pluginConfig.set("autospawn.panda", 0);
                pluginConfig.set("autospawn.drowned", 0);
            } else if (pluginConfig.getInt("config-version") == 11) {
                pluginConfig.set("other.spawners-enabled", true);
                pluginConfig.set("other.end-enderman-spawning", 10);
                pluginConfig.set("autospawn.fox", 0);
                pluginConfig.set("autospawn.panda", 0);
                pluginConfig.set("autospawn.drowned", 0);
            } else if (pluginConfig.getInt("config-version") == 10) {
                pluginConfig.set("other.kill-mobs-on-despawn", false);
                pluginConfig.set("other.spawners-enabled", true);
                pluginConfig.set("other.end-enderman-spawning", 10);
                pluginConfig.set("autospawn.fox", 0);
                pluginConfig.set("autospawn.panda", 0);
                pluginConfig.set("autospawn.drowned", 0);
            } else if (pluginConfig.getInt("config-version") == 9) {
                pluginConfig.set("other.spawn-no-spawning-area", -1);
                pluginConfig.set("other.kill-mobs-on-despawn", false);
                pluginConfig.set("other.spawners-enabled", true);
                pluginConfig.set("other.end-enderman-spawning", 10);
                pluginConfig.set("autospawn.fox", 0);
                pluginConfig.set("autospawn.panda", 0);
                pluginConfig.set("autospawn.drowned", 0);
            } else {
                plugin.getLogger().warning("MobPlugin's config file is outdated. Please delete the old config.");
                plugin.getLogger().error("Config error. The plugin will be disabled.");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return false;
            }

            pluginConfig.set("config-version", ver);
            pluginConfig.save();
            plugin.getLogger().notice("Config file updated to version " + ver);
        }

        spawnDelay = pluginConfig.getInt("entities.autospawn-ticks");
        noXpOrbs = true;//pluginConfig.getBoolean("other.use-no-xp-orbs");
        noSpawnEggWasting = pluginConfig.getBoolean("other.do-not-waste-spawn-eggs");
        despawnTicks = pluginConfig.getInt("entities.despawn-ticks");
        spawnerRange = pluginConfig.getInt("other.spawner-spawn-range");
        killOnDespawn = false;//pluginConfig.getBoolean("other.kill-mobs-on-despawn");
        endEndermanSpawnRate = pluginConfig.getInt("other.end-enderman-spawning");
        spawnersEnabled = pluginConfig.getBoolean("other.spawners-enabled");
        return true;
    }
}
