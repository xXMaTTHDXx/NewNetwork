package com.viperpvp.core.player;

import com.viperpvp.core.Core;
import com.viperpvp.core.events.NetworkJoinEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Matt on 10/08/2016.
 */
public class Connector {

    private Core plugin;

    public Connector(Core plugin) {
        this.plugin = plugin;
        observe();
    }

    private Observable<Event> observe() {
        Observable<Event> observable = plugin.observeEvent(EventPriority.HIGHEST, PlayerJoinEvent.class, PlayerQuitEvent.class).map(playerEvent -> playerEvent);

        observable.subscribe(new Action1<Event>() {
            @Override
            public void call(Event event) {
                if (event instanceof PlayerQuitEvent) {
                    ((PlayerQuitEvent) event).setQuitMessage(ChatColor.RED + ChatColor.BOLD.toString() + "[Leave] " + ((PlayerQuitEvent) event).getPlayer().getName());
                    plugin.getPlayerManager().logout(plugin.getPlayerManager().getPlayer(((PlayerQuitEvent) event).getPlayer()));
                }
                else if(event instanceof PlayerJoinEvent) {
                    ((PlayerJoinEvent) event).setJoinMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "[Join] " + ((PlayerJoinEvent) event).getPlayer().getName());
                    plugin.getPlayerManager().login(((PlayerJoinEvent) event).getPlayer());
                }
                else {

                }
            }
        });
        return observable;
    }
}
