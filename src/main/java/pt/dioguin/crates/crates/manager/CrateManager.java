package pt.dioguin.crates.crates.manager;

import org.bukkit.configuration.file.FileConfiguration;
import pt.dioguin.crates.CratesPlugin;
import pt.dioguin.crates.crates.Crate;
import pt.dioguin.crates.crates.rewards.Reward;

import java.util.ArrayList;
import java.util.List;

public class CrateManager {

    private final List<Crate> crates;

    public CrateManager(){
        this.crates = new ArrayList<>();
    }

    public void loadCrates(){

        FileConfiguration config = CratesPlugin.getInstance().getConfig();


        for (String name : config.getConfigurationSection("crates").getKeys(false)){


        }


    }

    public List<Reward> loadRewards(String path){

        FileConfiguration config = CratesPlugin.getInstance().getConfig();
        List<Reward> rewards = new ArrayList<>();

        for (String key : config.getConfigurationSection(path).getKeys(false)){



        }

        return rewards;
    }

    public Crate getCrate(String name){
        return this.crates.stream().filter(crate -> crate.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Crate> getCrates() {
        return crates;
    }
}
