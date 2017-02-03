package com.viperpvp.core.statistic;

import org.bukkit.entity.Player;
import rx.Observable;

/**
 * Created by Matt on 13/08/2016.
 */
public interface Statistic {


    String getName();
    void increment(int amount);
    Observable<Player> observe();
}
