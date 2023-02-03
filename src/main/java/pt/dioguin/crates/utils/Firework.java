package pt.dioguin.crates.utils;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import pt.dioguin.crates.utils.packets.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Firework{

    public static void spawnFirework(Location location, FireworkEffect effect, List<Player> list){
        if (list.isEmpty()) return;

        List<Player> players = new ArrayList<>(list);
        for (Player online : list)
        {
            if (!online.isOnline())
            {
                players.remove(online);
            }
        }

        int entityId=Firework.getNextEntityId();

        //Spawn
        new PacketHandlerSpawnEntity(entityId, 76/*firework entityId*/, location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch(), 0, 0, 0, 0).send(players);

        //Set FireworkMeta
        ItemStack fireworkItem=new ItemStack(Material.FIREWORK);
        FireworkMeta meta=(FireworkMeta)fireworkItem.getItemMeta();
        meta.addEffect(effect);
        fireworkItem.setItemMeta(meta);

        scWatchableObject[] metadata=new scWatchableObject[]{
                new scWatchableObject(5, 8, Firework.toNMSItemStack(fireworkItem)),
                new scWatchableObject(0, 4, (byte)0),
                new scWatchableObject(0, 3, (byte)0),
                new scWatchableObject(4, 2, ""),
                new scWatchableObject(1, 1, (short)300),
                new scWatchableObject(0, 0, (byte)0)
        };

        new PacketHandlerEntityMetadata(entityId, Arrays.asList(metadata)).send(players);

        //Play Effect
        new PacketHandlerEntityStatus(entityId, (byte)17/*Explode Status*/).send(players);

        //Destroy Entity (very important, otherwise clients will crash)
        new PacketHandlerEntityDestroy(entityId).send(players);
    }

    /**
     * Get a new entityID
     */
    private static int currentEntityId=Integer.MAX_VALUE;
    public static int getNextEntityId(){
        return Firework.currentEntityId++;
    }

    /**
     * Convert an org.bukkit.ItemStack to a net.minecraft.server.<version>.ItemStack
     */
    private static Method asNMSCopy;
    static{
        try{
            Firework.asNMSCopy=Class.forName("org.bukkit.craftbukkit."+
                    Bukkit.getServer().getClass().getName().split("\\.")[3]+
                    ".inventory.CraftItemStack").getDeclaredMethod("asNMSCopy", ItemStack.class);
        }catch(Exception a){
            a.printStackTrace();
        }
    }
    public static Object toNMSItemStack(ItemStack item){
        try{
            return Firework.asNMSCopy.invoke(null, item);
        }catch(Exception a){
            a.printStackTrace();
            return null;
        }
    }
}
