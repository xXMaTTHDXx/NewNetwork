package com.viperpvp.core;

import com.viperpvp.core.achievement.AchievementHandler;
import com.viperpvp.core.game.arena.ArenaManager;
import com.viperpvp.core.player.Connector;
import com.viperpvp.core.player.PlayerManager;
import com.viperpvp.core.nms.sql.SQLManager;
import com.viperpvp.core.statistic.StatisticHandler;
import tech.rayline.core.library.IgnoreLibraries;
import tech.rayline.core.plugin.RedemptivePlugin;

/**
 * Created by Matt on 09/08/2016.
 */
@IgnoreLibraries
public class Core extends RedemptivePlugin {

    private static Core instance;
    private AchievementHandler achievementHandler;
    private PlayerManager playerManager;
    private StatisticHandler statisticHandler;

    private SQLManager sqlManager;
    private ArenaManager arenaManager;

    @Override
    protected void onModuleEnable() throws Exception {
        instance = this;
        sqlManager = new SQLManager("localhost", "3306", "network", "minecraft", "test");
        playerManager = new PlayerManager(this);

        /**
         * Instantiate Connector
         */
        new Connector(this);

        achievementHandler = new AchievementHandler();
        statisticHandler = new StatisticHandler();
        this.arenaManager = new ArenaManager();

    }

    @Override
    protected void onModuleDisable() throws Exception {
        instance = null;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public static Core getInstance() {
        return instance;
    }

    public AchievementHandler getAchievementHandler() {
        return achievementHandler;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public SQLManager getSqlManager() {
        return sqlManager;
    }

    public StatisticHandler getStatisticHandler() {
        return statisticHandler;
    }

}
