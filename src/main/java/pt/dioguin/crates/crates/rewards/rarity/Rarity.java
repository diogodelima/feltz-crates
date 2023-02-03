package pt.dioguin.crates.crates.rewards.rarity;

import org.bukkit.Color;

public enum Rarity {

    common(Color.WHITE, "§f§l"),
    uncommon(Color.BLUE, "§9§l"),
    rare(Color.PURPLE, "§5§l"),
    legendary(Color.ORANGE, "§6§l");

    private final Color color;
    private final String chatColor;

    Rarity(Color color, String chatColor) {
        this.color = color;
        this.chatColor = chatColor;
    }

    public Color getColor() {
        return color;
    }

    public String getChatColor() {
        return chatColor;
    }
}
