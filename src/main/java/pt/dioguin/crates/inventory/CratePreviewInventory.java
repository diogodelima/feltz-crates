package pt.dioguin.crates.inventory;

import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pt.dioguin.crates.crates.rewards.Reward;
import pt.dioguin.crates.utils.ItemBuilder;

import java.util.List;

public class CratePreviewInventory extends View {

    public CratePreviewInventory(){
        super(45, "§l§nRewards Preview");
        setCancelOnClick(true);
    }

    @Override
    protected void onRender(@NotNull ViewContext context) {

        final List<Reward> rewards = context.get("rewards");

        int slot = 10;

        for (Reward reward : rewards){

            if (slot == 17 || slot == 26 || slot == 35)
                slot += 2;

            ItemStack item = new ItemBuilder(reward.getItem().clone())
                    .addLore(
                            "",
                            "§fChance: §7" + reward.getChanceFormatted() + "%"
                    )
                    .build();

            context.slot(slot, item);
            slot++;
        }

    }
}
