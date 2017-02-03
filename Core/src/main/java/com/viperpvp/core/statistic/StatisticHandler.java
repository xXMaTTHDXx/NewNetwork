package com.viperpvp.core.statistic;

import com.viperpvp.core.Core;
import com.viperpvp.core.achievement.Achievement;
import com.viperpvp.core.achievement.FirstJoin;
import com.viperpvp.core.achievement.PunchTheAuthority;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 13/08/2016.
 */
public class StatisticHandler {

    private List<Statistic> allStatistics = new ArrayList<>();

    public StatisticHandler() {
        init();
    }

    public Statistic getFromString(String name) {
        for (Statistic s : allStatistics) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public void init() {
        allStatistics.clear();
    }

    public boolean hasStatistic(Player player, Statistic statistic) {
        return Core.getInstance().getPlayerManager().getPlayer(player).getMeta().getStats().contains(statistic);
    }

    public void addStatistic(Statistic statistic) {
        if (allStatistics.contains(statistic)) {
            return;
        }
        allStatistics.add(statistic);
    }
}
