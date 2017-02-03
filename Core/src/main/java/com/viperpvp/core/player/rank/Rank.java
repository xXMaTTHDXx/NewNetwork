package com.viperpvp.core.player.rank;

import org.bukkit.ChatColor;

/**
 * Created by Matt on 10/08/2016.
 */
public enum  Rank {
    OWNER("Owner", ChatColor.RED + ChatColor.BOLD.toString() + "OWNER", 0),
    CO_OWNER("Co-Owner", ChatColor.RED + ChatColor.BOLD.toString() + "CO OWNER", 0),
    ADMIN("Admin", ChatColor.DARK_RED + "Admin", 1),
    MOD("Mod", ChatColor.GREEN + "Mod", 2),
    DIAMOND("Diamond", ChatColor.AQUA + ChatColor.BOLD.toString() + "DIAMOND", 3),
    GOLD("Gold", ChatColor.GOLD + ChatColor.BOLD.toString() + "GOLD", 3),
    IRON("Iron", ChatColor.GRAY + ChatColor.BOLD.toString() + "IRON", 3),
    DEFAULT("Default", "", 4);
    String name;
    String displayName;
    int priority;

    Rank(String name, String displayName, int priority) {
        this.name = name;
        this.displayName = displayName;
        this.priority = priority;
    }
}
