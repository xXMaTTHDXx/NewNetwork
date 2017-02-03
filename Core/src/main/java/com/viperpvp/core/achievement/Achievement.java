package com.viperpvp.core.achievement;

import com.viperpvp.core.player.CPlayer;
import org.bukkit.entity.Player;
import rx.Observable;

/**
 * Created by Matt on 09/08/2016.
 */
public interface Achievement {

    String getName();

    Observable<Player> observe();
    void onAchieve(Player player);
}
