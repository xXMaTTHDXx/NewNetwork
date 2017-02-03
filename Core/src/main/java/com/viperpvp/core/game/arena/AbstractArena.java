package com.viperpvp.core.game.arena;

import org.bukkit.Location;

import java.util.List;

/**
 * Created by Matt on 23/08/2016.
 */
public class AbstractArena {

    private String name;
    private List<Location> spawns;

    public AbstractArena(String name, List<Location> spawns) {
        this.name = name;
        this.spawns = spawns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public void setSpawns(List<Location> spawns) {
        this.spawns = spawns;
    }
}
