package pt.dioguin.crates.crates;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pt.dioguin.crates.CratesPlugin;
import pt.dioguin.crates.crates.rewards.Reward;
import pt.dioguin.crates.inventory.CratePreviewInventory;

import java.util.ArrayList;
import java.util.HashMap;
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
        spawnHologram(this.location);
    }

    public Crate(String name, ItemStack item, Location location, List<Reward> rewards){
        this.name = name;
        this.item = item;
        this.location = location;
        this.rewards = rewards;
        CratesPlugin.getCrateManager().getCrates().add(this);
        spawnHologram(this.location);
    }

    public void interact(Player player){

        ItemStack item = player.getItemInHand();

        if (item.getType() == Material.AIR) return;

        if (!item.isSimilar(this.item)){
            player.sendMessage("§cThis key isn't valid for opening the crate");
            player.playSound(player.getLocation(), Sound.BAT_DEATH, 1, 1);
            player.setVelocity(player.getLocation().getDirection().multiply(-1));
            return;
        }

        open(player);
    }

    public void open(Player player){

    }

    public void preview(Player player){
        CratesPlugin.getInstance().getViewFrame().open(CratePreviewInventory.class, player, new HashMap<String, Object>(){{put("rewards", rewards);}});
    }

    private void spawnHologram(Location location){

        if (location == null) return;

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
