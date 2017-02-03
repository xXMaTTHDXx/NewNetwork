package com.viperpvp.core.achievement;

import com.viperpvp.core.Core;
import com.viperpvp.core.events.NetworkJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import tech.rayline.core.rx.EventStreamer;

/**
 * Created by Matt on 09/08/2016.
 */
public class FirstJoin implements Achievement {

    public FirstJoin() {
        observe();
    }

    @Override
    public String getName() {
        return "First Join";
    }

    @Override
    public Observable<Player> observe() {
        Observable<Player> observable;

        observable = Core.getInstance().observeEvent(NetworkJoinEvent.class)
                .filter(event -> !Core.getInstance().getAchievementHandler().hasAchievement(event.getPlayer(), this))
                .map(NetworkJoinEvent::getPlayer);

        observable.subscribe(this::onAchieve);
        return observable;
    }

    @Override
    public void onAchieve(Player player) {
        player.sendMessage(ChatColor.GREEN + ">> Achievement Unlocked: " + ChatColor.GOLD + getName() + ChatColor.GREEN + " <<");
        Core.getInstance().getPlayerManager().getPlayer(player).getMeta().getAchievements().add(this);
    }
}
