package pt.dioguin.crates;

import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pt.dioguin.crates.command.CrateCommand;
import pt.dioguin.crates.crates.manager.CrateManager;
import pt.dioguin.crates.inventory.CratePreviewInventory;
import pt.dioguin.crates.listener.PlayerListener;

public final class CratesPlugin extends JavaPlugin {

    private static CrateManager crateManager;
    private ViewFrame viewFrame;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        crateManager = new CrateManager();
        crateManager.loadCrates();
        viewFrame = ViewFrame.of(this, new CratePreviewInventory());
        viewFrame.register();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("crate").setExecutor(new CrateCommand());
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

    public ViewFrame getViewFrame() {
        return viewFrame;
    }
}
