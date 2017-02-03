package com.viperpvp.core.achievement;

import com.viperpvp.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import rx.Observable;

/**
 * Created by Matt on 10/08/2016.
 */
public class PunchTheAuthority implements Achievement {

    private Core plugin = Core.getInstance();

    public PunchTheAuthority() {
        observe();
    }

    @Override
    public String getName() {
        return "Punch The Authority";
    }

    @Override
    public Observable<Player> observe() {
        Observable<Player> observable = plugin.observeEvent(EventPriority.MONITOR, EntityDamageByEntityEvent.class)
                .filter(event -> event.getDamager() instanceof Player && event.getEntity() instanceof Player &&
                        !plugin.getAchievementHandler().hasAchievement((Player)event.getDamager(), this))
                .filter(event -> ((Player) event.getEntity()).getName().equalsIgnoreCase("GitBash"))
                .map(event -> ((Player)event.getDamager()));

        observable.subscribe(this::onAchieve);
        return observable;
    }

    @Override
    public void onAchieve(Player player) {
        player.sendMessage(ChatColor.GREEN + ">> Achievement Unlocked: " + ChatColor.GOLD + getName() + ChatColor.GREEN + " <<");
        plugin.getPlayerManager().getPlayer(player).getMeta().getAchievements().add(this);
    }
}
