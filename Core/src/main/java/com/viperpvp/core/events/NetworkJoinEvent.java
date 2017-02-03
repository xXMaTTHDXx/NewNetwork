package com.viperpvp.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Matt on 10/08/2016.
 */
public class NetworkJoinEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;

    public NetworkJoinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
