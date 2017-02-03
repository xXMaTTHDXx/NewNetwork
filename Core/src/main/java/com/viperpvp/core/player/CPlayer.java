package com.viperpvp.core.player;

import com.viperpvp.core.achievement.Achievement;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Matt on 10/08/2016.
 */
public class CPlayer {

    public Player bukkitPlayer;
    public PlayerMeta meta;

    public CPlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }

    public void setBukkitPlayer(Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }

    public PlayerMeta getMeta() {
        return meta;
    }

    public void setMeta(PlayerMeta meta) {
        this.meta = meta;
    }
}
