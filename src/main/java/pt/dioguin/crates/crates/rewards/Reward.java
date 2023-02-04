package pt.dioguin.crates.crates.rewards;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pt.dioguin.crates.crates.rewards.rarity.Rarity;
import pt.dioguin.crates.utils.Firework;

import java.util.Arrays;
import java.util.Collections;

public class Reward {

    private final ItemStack item;
    private final String command;
    private final double chance;
    private final Rarity rarity;

    public Reward(ItemStack item, String command, double chance, Rarity rarity){
        this.item = item;
        this.command = command;
        this.chance = chance;
        this.rarity = rarity;
    }

    public void give(Player player, Location location){

        if (player.getItemInHand().getAmount() > 1) player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        else player.setItemInHand(null);

        if (this.command.equalsIgnoreCase("none")) player.getInventory().addItem(item);
        else Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command.replace("{player}", player.getName()));

        Firework.spawnFirework(location.clone().add(0.5, 1, 0.5), FireworkEffect.builder().withColor(this.rarity.getColor()).build(), Collections.singletonList(player));
        Bukkit.broadcastMessage("§eThe player " + player.getName() + " §ehas collected a " + this.rarity.getChatColor() + this.rarity.name().toUpperCase() + " §eitem in the crate!");
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

    public int getChanceFormatted(){
        return (int) (getChance() * 100);
    }
}
