package pt.dioguin.crates.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.dioguin.crates.CratesPlugin;
import pt.dioguin.crates.crates.Crate;
import pt.dioguin.crates.listener.PlayerListener;

public class CrateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("§cOnly players can execute this command.");
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
