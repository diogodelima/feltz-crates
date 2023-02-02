package pt.dioguin.crates;

import org.bukkit.plugin.java.JavaPlugin;
import pt.dioguin.crates.crates.manager.CrateManager;

public final class CratesPlugin extends JavaPlugin {

    private static CrateManager crateManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        crateManager = new CrateManager();
        crateManager.loadCrates();
    }

    @Override
    public void onDisable() {
        crateManager.save();
    }

    public static CrateManager getCrateManager() {
        return crateManager;
    }

    public static CratesPlugin getInstance(){
        return getPlugin(CratesPlugin.class);
    }

}
