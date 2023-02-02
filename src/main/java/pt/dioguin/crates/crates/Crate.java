package pt.dioguin.crates.crates;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import pt.dioguin.crates.CratesPlugin;

public class Crate {

    private final String name;
    private ItemStack item;
    private Location location;

    public Crate(String name){
        this.name = name;
        CratesPlugin.getCrateManager().getCrates().add(this);
    }

    public Crate(String name, ItemStack item, Location location){
        this.name = name;
        this.item = item;
        this.location = location;
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
