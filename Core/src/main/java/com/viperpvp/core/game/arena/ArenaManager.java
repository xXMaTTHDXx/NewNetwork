package com.viperpvp.core.game.arena;

import com.viperpvp.core.Core;
import com.viperpvp.core.game.GameManager;
import com.viperpvp.core.game.Minigame;
import com.viperpvp.core.util.UtilLocation;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Matt on 06/09/2016.
 */
public class ArenaManager {

    private HashMap<Class<? extends Minigame>, List<AbstractArena>> arenaList = new HashMap<>();

    private File arenaFile;
    private FileConfiguration arenas;

    public ArenaManager() {
        arenaFile = new File(Core.getInstance().getDataFolder(), "arenaDat.yml");

        if (!arenaFile.exists()) {
            try {
                arenaFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        arenas = YamlConfiguration.loadConfiguration(arenaFile);

        for (String game : arenas.getKeys(false)) {
            Class<? extends Minigame> clazz = GameManager.getInstance().getMinigame(game);
            List<AbstractArena> gameArenas = new ArrayList<>();

            for (String map : arenas.getConfigurationSection(game + ".maps").getKeys(false)) {

                List<Location> spawns = new ArrayList<>();

                for (String location : arenas.getStringList(game + ".maps." + map + ".locations")) {
                    Location loc = UtilLocation.getLocationFromString(location);
                    spawns.add(loc);
                }
                AbstractArena arena = new AbstractArena(map, spawns);
                gameArenas.add(arena);
            }
            arenaList.put(clazz, gameArenas);
        }
    }

    public AbstractArena getArenaByName(Class<? extends Minigame> minigame, String name) {
        Validate.isTrue(arenaList.containsKey(minigame));
        List<AbstractArena> gameMaps = arenaList.get(minigame);

        for (AbstractArena a : gameMaps) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public AbstractArena getRandomArena(Class<? extends Minigame> minigame) {
        Random ran = new Random();
        Validate.isTrue(arenaList.containsKey(minigame));

        List<AbstractArena> gameMaps = arenaList.get(minigame);

        int ranInt = ran.nextInt(gameMaps.size());
        return gameMaps.get(ranInt);
    }
}
