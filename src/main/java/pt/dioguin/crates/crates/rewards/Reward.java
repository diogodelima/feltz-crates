package pt.dioguin.crates.crates.rewards;

import org.bukkit.inventory.ItemStack;

public class Reward {

    private final ItemStack item;
    private final String command;
    private final double chance;

    public Reward(ItemStack item, String command, double chance){
        this.item = item;
        this.command = command;
        this.chance = chance;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getCommand() {
        return command;
    }

    public double getChance() {
        return chance;
    }
}
