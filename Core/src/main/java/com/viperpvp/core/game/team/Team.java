package com.viperpvp.core.game.team;

import com.viperpvp.core.player.CPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 23/08/2016.
 */
public class Team {

    private String name;
    private int maxSize;
    private List<CPlayer> members;

    public Team(String name) {
        this.name = name;
        this.maxSize = -1;
        this.members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public List<CPlayer> getMembers() {
        return members;
    }

    public void setMembers(List<CPlayer> members) {
        this.members = members;
    }
}
