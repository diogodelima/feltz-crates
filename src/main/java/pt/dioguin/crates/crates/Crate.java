package pt.dioguin.crates.crates;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import pt.dioguin.crates.CratesPlugin;
import pt.dioguin.crates.crates.rewards.Reward;

import java.util.ArrayList;
import java.util.List;

public class Crate {

    private final String name;
    private final List<Reward> rewards;
    private ItemStack item;
    private Location location;

    public Crate(String name){
        this.name = name;
        this.rewards = new ArrayList<>();
        CratesPlugin.getCrateManager().getCrates().add(this);
    }

    public Crate(String name, ItemStack item, Location location, List<Reward> rewards){
        this.name = name;
        this.item = item;
        this.location = location;
        this.rewards = rewards;
        CratesPlugin.getCrateManager().getCrates().add(this);
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
