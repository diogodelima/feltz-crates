package pt.dioguin.crates.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pt.dioguin.crates.CratesPlugin;
import pt.dioguin.crates.crates.Crate;

public class PlayerListener implements Listener {

    public static Location location;

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event){

        if (event.getClickedBlock() == null) return;

        Player player = event.getPlayer();
        Crate crate = CratesPlugin.getCrateManager().getCrate(event.getClickedBlock().getLocation());

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && crate != null) {
            crate.interact(player);
            event.setCancelled(true);
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK && crate != null) {
            crate.preview(player);
            event.setCancelled(true);
        }

        if (player.getItemInHand().getType() != Material.IRON_AXE || event.getAction() != Action.RIGHT_CLICK_BLOCK || !player.hasPermission("crates.admin")) return;

        location = event.getClickedBlock().getLocation();
        player.sendMessage("§aLocation selected successfully.");
    }

}
