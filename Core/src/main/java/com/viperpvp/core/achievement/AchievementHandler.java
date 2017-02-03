package com.viperpvp.core.achievement;

import com.viperpvp.core.Core;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/08/2016.
 */
public class AchievementHandler {

    private List<Achievement> allAchievements = new ArrayList<>();

    public AchievementHandler() {
        init();
    }

    public Achievement getFromString(String name) {
        for (Achievement a : allAchievements) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public void init() {
        allAchievements.clear();
        allAchievements.add(new FirstJoin());
        allAchievements.add(new PunchTheAuthority());
    }

    public boolean hasAchievement(Player player, Achievement achievement) {
        System.out.println(player == null);
        System.out.println(achievement == null);
        System.out.println(Core.getInstance().getPlayerManager() == null);
        System.out.println(Core.getInstance().getPlayerManager().getPlayer(player) == null);
        System.out.println(Core.getInstance().getPlayerManager().getPlayer(player).getMeta() == null);
        System.out.println(Core.getInstance().getPlayerManager().getPlayer(player).getMeta().getAchievements() == null);

        return Core.getInstance().getPlayerManager().getPlayer(player).getMeta().getAchievements().contains(achievement);
    }

    public void addAchievement(Achievement achievement) {
        if (allAchievements.contains(achievement)) {
            return;
        }

        allAchievements.add(achievement);
    }
}
