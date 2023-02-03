package pt.dioguin.crates.crates.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pt.dioguin.crates.CratesPlugin;
import pt.dioguin.crates.crates.Crate;
import pt.dioguin.crates.crates.rewards.Reward;
import pt.dioguin.crates.crates.rewards.rarity.Rarity;
import pt.dioguin.crates.utils.ItemBuilder;
import pt.dioguin.crates.utils.Serializer;

import java.util.ArrayList;
import java.util.List;

public class CrateManager {

    private final List<Crate> crates;

    public CrateManager(){
        this.crates = new ArrayList<>();
    }

    public void save(){

        FileConfiguration config = CratesPlugin.getInstance().getConfig();

        for (Crate crate : this.crates){
            if (crate.getLocation() == null) continue;
            config.set("crates." + crate.getName() + ".location", Serializer.locationSerializer(crate.getLocation()));
        }

        CratesPlugin.getInstance().saveConfig();
    }

    public void loadCrates(){

        FileConfiguration config = CratesPlugin.getInstance().getConfig();

        for (String name : config.getConfigurationSection("crates").getKeys(false)){

            Location location = null;
            if (config.contains("crates." + name + ".location"))
                location = Serializer.locationDeserializer(config.getString("crates." + name + ".location"));

            ItemStack display = new ItemBuilder(Material.getMaterial(config.getString("crates." + name + ".display.material")), 1, (short) config.getInt("crates." + name + ".display.data"))
                    .name(config.getString("crates." + name + ".display.name").replace("&", "§"))
                    .setLore(config.getStringList("crates." + name + ".display.lore"))
                    .glow(config.getBoolean("crates." + name + ".display.glow"))
                    .build();

            new Crate(name, display, location, loadRewards("crates." + name + ".rewards"));
        }

        Bukkit.getConsoleSender().sendMessage("§a[feltz-crates] §f" + this.crates.size() + " §acrates have been successfully loaded");
    }

    public List<Reward> loadRewards(String path){

        FileConfiguration config = CratesPlugin.getInstance().getConfig();
        List<Reward> rewards = new ArrayList<>();

        for (String key : config.getConfigurationSection(path).getKeys(false)){

            ItemStack item = new ItemBuilder(Material.getMaterial(config.getString(path + "." + key + ".material")), config.getInt(path + "." + key + ".amount"), (short) config.getInt(path + "." + key + ".data"))
                    .changeItemMeta(meta -> {

                        if (config.contains(path + "." + key + ".name"))
                            meta.setDisplayName(config.getString(path + "." + key + ".name").replace("&" ,"§"));

                        if (config.contains(path + "." + key + ".lore")){
                            List<String> lore = config.getStringList(path + "." + key + ".lore");
                            lore.replaceAll(s -> s.replace("&" ,"§"));
                            meta.setLore(lore);
                        }

                        if (config.contains(path + "." + key + ".enchants")){

                            for (String enchant : config.getStringList(path + "." + key + ".enchants")){
                                Enchantment enchantment = Enchantment.getByName(enchant.split(":")[0]);
                                int level = Integer.parseInt(enchant.split(":")[1]);
                                meta.addEnchant(enchantment, level, true);
                            }

                        }

                    })
                    .glow(config.getBoolean(path + "." + key + ".glow"))
                    .build();

            String command = config.getString(path + "." + key + ".command");
            String rarity = config.getString(path + "." + key + ".rarity");
            double chance = config.getDouble(path + "." + key + ".chance");
            rewards.add(new Reward(item, command, chance, Rarity.valueOf(rarity)));
        }

        return rewards;
    }

    public Crate getCrate(String name){
        return this.crates.stream().filter(crate -> crate.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Crate getCrate(Location location){
        return this.crates.stream().filter(crate -> crate.getLocation().equals(location)).findFirst().orElse(null);
    }

    public List<Crate> getCrates() {
        return crates;
    }
}
