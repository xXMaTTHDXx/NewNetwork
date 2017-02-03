package com.viperpvp.core.game.events;

import com.viperpvp.core.game.Minigame;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Matt on 02/09/2016.
 */
public class LobbyTickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Minigame minigame;

    public LobbyTickEvent(Minigame minigame) {
        this.minigame = minigame;
    }

    public Minigame getMinigame() {
        return minigame;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
