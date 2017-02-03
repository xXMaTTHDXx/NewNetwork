package io.matthd.testgame;

import com.viperpvp.core.Core;
import com.viperpvp.core.game.EventListener;
import com.viperpvp.core.game.GameFlags;
import com.viperpvp.core.game.Minigame;
import com.viperpvp.core.game.arena.AbstractArena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Matt on 06/09/2016.
 */
public class TestGame extends Minigame {

    public TestGame(String name) {
        super(name, new GameFlags(false, false, true, false));
    }

    @Override
    public void onRegister() {
        addEvent(new EventListener<PlayerJoinEvent>() {
            @Override
            public void call(PlayerJoinEvent playerJoinEvent) {
                Player pl = playerJoinEvent.getPlayer();
                pl.sendMessage(ChatColor.RED + "These work");
            }
        });
    }

    @Override
    public void onStart() {

    }
}
