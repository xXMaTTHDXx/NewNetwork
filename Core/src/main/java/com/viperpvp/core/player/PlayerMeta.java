package com.viperpvp.core.player;

import com.viperpvp.core.Core;
import com.viperpvp.core.achievement.Achievement;
import com.viperpvp.core.player.rank.Rank;
import com.viperpvp.core.statistic.Statistic;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/08/2016.
 */
public class PlayerMeta {

    private int coins;
    private List<Achievement> achievements;
    private Rank rank;
    private List<Statistic> stats;

    public PlayerMeta(Player bukkitPlayer) {
        this.coins = 0;
        this.achievements = new ArrayList<>();
        this.stats = new ArrayList<>();
        this.rank = Rank.DEFAULT;
    }

    private PlayerMeta(Player bukkitPlayer, int coins, List<Achievement> achievements, Rank rank, List<Statistic> stats) {
        this.coins = coins;
        this.achievements = achievements;
        this.rank = rank;
        this.stats = stats;
    }

    public PlayerMeta(ResultSet set) throws Exception {
        this.achievements = deserializeAchievements(set.getString("Achievements"));
        this.coins = set.getInt("Coins");
        this.rank = Rank.valueOf(set.getString("Rank"));
        this.stats = deserializeStatistics(set.getString("Statistics"));
    }

    private List<Achievement> deserializeAchievements(String serialized) {
        List<Achievement> local = new ArrayList<>();
        for (String s : serialized.split(",")) {
            Achievement a = Core.getInstance().getAchievementHandler().getFromString(s);
            local.add(a);
        }
        return local;
    }

    private List<Statistic> deserializeStatistics(String serialized) {
        List<Statistic> local = new ArrayList<>();
        for (String s : serialized.split(",")) {
            Statistic a = Core.getInstance().getStatisticHandler().getFromString(s);
            local.add(a);
        }
        return local;
    }

    public int getCoins() {
        return coins;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public List<Statistic> getStats() {
        return stats;
    }

    public void setStats(List<Statistic> stats) {
        this.stats = stats;
    }
}
