package com.viperpvp.core.player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.viperpvp.core.Core;
import com.viperpvp.core.achievement.Achievement;
import com.viperpvp.core.events.NetworkJoinEvent;
import com.viperpvp.core.player.rank.Rank;
import com.viperpvp.core.statistic.Statistic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/08/2016.
 */
public class PlayerManager {

    private List<CPlayer> onlinePlayers;
    private Core plugin;

    public PlayerManager(Core plugin) {
        this.plugin = plugin;
        onlinePlayers = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(this::login);
    }

    public void login(Player player) {
        CPlayer cPlayer = new CPlayer(player);
        ResultSet result = null;
        try {
            PreparedStatement st = plugin.getSqlManager().getConnection().prepareStatement("SELECT * FROM `users` WHERE Uuid=?");
            st.setString(1, player.getUniqueId().toString());
            result = st.executeQuery();

            PlayerMeta meta = null;
            if(result.next()) {
                meta = new PlayerMeta(result);
            }
            else {
                meta = new PlayerMeta(player);
                try {
                    PreparedStatement statement = plugin.getSqlManager().getConnection().prepareStatement("INSERT INTO `users` (Uuid, Coins, Achievements, Rank, Statistics) VALUES (?,?,?,?,?)");
                    statement.setString(1, player.getUniqueId().toString());
                    statement.setInt(2, 0);
                    statement.setString(3, null);
                    statement.setString(4, Rank.DEFAULT.toString());
                    statement.setString(5, null);
                    statement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            cPlayer.setMeta(meta);
            onlinePlayers.add(cPlayer);
            plugin.getServer().getPluginManager().callEvent(new NetworkJoinEvent(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(CPlayer player) {
        int coins = player.getMeta().getCoins();
        List<Achievement> achievements = player.getMeta().getAchievements();
        List<Statistic> statistics = player.getMeta().getStats();

        StringBuilder sb = new StringBuilder();

        for (Achievement achievement : achievements) {
            sb.append(achievement.getName()).append(",");
        }

        StringBuilder statBuilder = new StringBuilder();

        for (Statistic s : statistics) {
            statBuilder.append(s.getName()).append(",");
        }


        try {
            PreparedStatement statement = plugin.getSqlManager().getConnection().prepareStatement("UPDATE `users` SET Coins=?, Achievements=?, Rank=?, Statistics=? WHERE Uuid=?");
            statement.setInt(1, coins);
            statement.setString(2, sb.toString());
            statement.setString(3, player.getMeta().getRank().toString());
            statement.setString(4, statBuilder.toString());
            statement.setString(5, player.getBukkitPlayer().getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onlinePlayers.remove(player);
    }

    public CPlayer getPlayer(Player player) {
        for (CPlayer cPlayer : onlinePlayers) {
            if (cPlayer.getBukkitPlayer().getName().equalsIgnoreCase(player.getName())) {
                return cPlayer;
            }
        }
        return null;
    }
}
