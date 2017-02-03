package com.viperpvp.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by Matt on 13/09/2016.
 */
public class UtilLocation {

    public static Location getLocationFromString(String s) {
        String[] parse = s.split(",");
        World world = Bukkit.getWorld(parse[0]);
        int x = Integer.parseInt(parse[1]);
        int y = Integer.parseInt(parse[2]);
        int z = Integer.parseInt(parse[3]);

        return new Location(world, x, y, z);
    }

    public static String getStringFromLocation(Location loc) {
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }
}
