package com.viperpvp.core.nms;

import org.bukkit.Bukkit;

/**
 * Created by Matt on 05/09/2016.
 */
public class ReflectionAPI {

    /**
     * The version of CraftBukkit/NMS
     */
    public static final String version;

    static {
        // set the version of CraftBukkit/NMS
        version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    /**
     * Private constructor to prevent instances
     */
    private ReflectionAPI() {}

    /**
     * Get the version of the server
     *
     * @return The version
     */
    public static String getVersion() {
        return version;
    }

    /**
     * Get a CraftBukkit class from its name
     *
     * @param name The class's name
     *
     * @return The CraftBukkit class, or null if not found
     */
    public static Class<?> getCraftClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Get a net.minecraft.server (NMS) class from its name
     *
     * @param name The class's name
     *
     * @return The NMS class, or null if not found
     */
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
