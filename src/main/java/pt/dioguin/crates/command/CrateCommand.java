package pt.dioguin.crates.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pt.dioguin.crates.CratesPlugin;
import pt.dioguin.crates.crates.Crate;
import pt.dioguin.crates.listener.PlayerListener;

public class CrateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)){

            if (args.length != 4){
                sender.sendMessage("§cTry type '/crate give <player> <type> <amount>'");
                return true;
            }

            if (args[0].equalsIgnoreCase("give")){

                Player target = Bukkit.getPlayerExact(args[1]);
                Crate crate = CratesPlugin.getCrateManager().getCrate(args[2]);
                int amount;

                if (target == null || !target.isOnline()){
                    sender.sendMessage("§cThis player isn't online");
                    return true;
                }

                if (crate == null){
                    sender.sendMessage("§cThis crate doesn't exists.");
                    return true;
                }

                try {
                    amount = Integer.parseInt(args[3]);
                }catch (Exception e){
                    sender.sendMessage("§cInvalid amount!");
                    return true;
                }

                ItemStack item = crate.getItem().clone();
                item.setAmount(amount);
                target.getInventory().addItem(item);
                sender.sendMessage("§aThe crates have been successfully sent.");
                return true;
            }

            sender.sendMessage("§cTry type '/crate give <player> <type> <amount>'");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("crate.admin")){
            player.performCommand("warp");
            return true;
        }

        if (args.length != 2){
            player.sendMessage("§cTry type '/crate setlocation <crate>'");
            return true;
        }

        if (args[0].equalsIgnoreCase("setlocation")){

            String name = args[1];
            Crate crate = CratesPlugin.getCrateManager().getCrate(name);
            Location location = PlayerListener.location;

            if (crate == null){
                player.sendMessage("§cThis crates does not exists.");
                return true;
            }

            if (location == null){
                player.sendMessage("§cInvalid location!");
                return true;
            }

            crate.setLocation(location);
            player.sendMessage("§aLocation of crate updated successfully.");
            return true;
        }

        player.sendMessage("§cTry type '/crate setlocation <crate>'");
        return false;
    }
}
