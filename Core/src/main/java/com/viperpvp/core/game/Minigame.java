package com.viperpvp.core.game;

import com.sun.javafx.event.EventUtil;
import com.viperpvp.core.Core;
import com.viperpvp.core.game.arena.AbstractArena;
import com.viperpvp.core.game.arena.ArenaManager;
import com.viperpvp.core.game.events.LobbyTickEvent;
import com.viperpvp.core.game.events.PostGameTickEvent;
import com.viperpvp.core.game.kit.Kit;

import static com.viperpvp.core.nms.ReflectionAPI.*;

import com.viperpvp.core.game.team.Team;
import com.viperpvp.core.player.CPlayer;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import tech.rayline.core.plugin.RedemptivePlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Matt on 23/08/2016.
 */
public abstract class Minigame {

    private String name;
    private List<EventListener> events;
    private List<Team> teams;

    private AbstractArena currentArena;
    private List<CPlayer> allPlayers;

    private HashMap<CPlayer, Kit> kitSelections = new HashMap<>();

    private int maxPlayers = 16;
    private int minPlayers = 1;

    private int lobbyPeriod = 60;
    private int lobbyCountPeriod = 10;
    private int postGamePeriod = 10;

    private GameState state = GameState.WAITING;

    private GameFlags flags;
    private static ArenaManager arenaManager;

    public Minigame(String name, GameFlags flags) {
        this.name = name;
        this.currentArena = currentArena;
        this.allPlayers = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.flags = flags;
        arenaManager = Core.getInstance().getArenaManager();
    }

    public final boolean isJoinable() {
        return state == GameState.WAITING || state == GameState.STARTING;
    }

    public final void unregisterListeners() {
        events.forEach(EventListener::unsubscribe);
    }

    public void addEvent(EventListener eventListener) {
        Validate.isTrue(!events.contains(eventListener));
        Core.getInstance().observeEvent(eventListener.getClass().asSubclass(Event.class));
        events.add(eventListener);
    }

    public static final AbstractArena randomMap(Class<? extends Minigame> game) {
        return arenaManager.getRandomArena(game);
    }

    public final void registerListeners() {
        events.forEach(eventListener -> eventListener.setActive(true));
    }

    public final void setState(GameState state) {
        if (state == this.state) return;

        if (state == GameState.WAITING) {
            unregisterListeners();
        }

        if (state == GameState.POST_GAME) {
            new BukkitRunnable() {
                int i = postGamePeriod;

                public void run() {
                    if (i > 0) {
                        Bukkit.getPluginManager().callEvent(new PostGameTickEvent(Minigame.this));
                    } else {
                        broadcastToAll("Teleporting to Lobby...");
                        home();
                    }
                }
            }.runTaskTimer(Core.getInstance(), 0L, 20L);
        } else if (state == GameState.STARTING) {
            int i = lobbyCountPeriod;
            new BukkitRunnable() {
                int i = lobbyCountPeriod;

                public void run() {
                    if (i > 0) {
                        Bukkit.getPluginManager().callEvent(new LobbyTickEvent(Minigame.this));
                    } else {
                        broadcastToAll("Telelporting to game!");
                        setState(GameState.PRE_GAME);
                        registerListeners();
                    }
                    i--;
                }
            }.runTaskTimer(Core.getInstance(), 0L, 20L);
        } else if (state == GameState.PRE_GAME) {

        }
    }

    public final void clearTeams() {
        for (Team t : teams) {
            t.getMembers().clear();
        }
    }

    public final void home() {
        for (CPlayer player : allPlayers) {
            player.bukkitPlayer.teleport(new Location(Bukkit.getWorld("world"), 0, 0, 0));
        }
        allPlayers.clear();
    }

    public final List<CPlayer> collectFromTeams() {
        List<CPlayer> all = new ArrayList<>();
        for (Team t : teams) {
            all.addAll(t.getMembers());
        }
        return all;
    }

    public final boolean sendActionBarMessage(Player player, String message) {
        try {
            Object chatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");
            Object playChat = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), Byte.TYPE).newInstance(chatSerializer, (byte) 2);
            Object handle = player.getClass().getMethod("getHandle", (Class<?>[]) new Class[0]).invoke(player, new Object[0]);
            Object connection = handle.getClass().getField("playerConnection").get(handle);
            connection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(connection, playChat);
            return true;
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Display a title with an optional subtitle to a player
     *
     * @param player   The player to display the message to
     * @param title    The title to send
     * @param subtitle The subtitle to send
     * @param fadeIn   The amount of time in ticks it takes to fade in
     * @param stay     The amount of time in ticks the message stays
     * @param fadeOut  The amount of time in ticks it takes to fade out
     * @return Whether the function finished successfully
     */
    // TODO: Make use of a future Spigot API to do this
    public final boolean sendTitleMessage(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        try {
            Method chatSerializerA = getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class);
            Class<?> enumTitleAction = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
            Object chatSerializerTitle = chatSerializerA.invoke(null, "{\"text\":\"" + title + "\"}");
            Object chatSerializerSubtitle = chatSerializerA.invoke(null, "{\"text\":\"" + subtitle + "\"}");
            Object enumTitle = enumTitleAction.getField("TITLE").get(null);
            Object enumSubtitle = enumTitleAction.getField("SUBTITLE").get(null);
            Object playTitle = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle$EnumTitleAction"), getNMSClass("IChatBaseComponent")).newInstance(enumTitle, chatSerializerTitle);
            Object playSubtitle = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle$EnumTitleAction"), getNMSClass("IChatBaseComponent")).newInstance(enumSubtitle, chatSerializerSubtitle);
            Object playTime = getNMSClass("PacketPlayOutTitle").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(fadeIn, stay, fadeOut);
            Object entityPlayer = player.getClass().getMethod("getHandle", (Class<?>[]) new Class[0]).invoke(player, new Object[0]);
            Object connection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Method sendPacket = connection.getClass().getMethod("sendPacket", getNMSClass("Packet"));
            sendPacket.invoke(connection, playTitle);
            if (subtitle != null && !"".equals(subtitle))
                sendPacket.invoke(connection, playSubtitle);
            sendPacket.invoke(connection, playTime);
            return true;
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void broadcastToAll(String msg) {
        for (CPlayer player : allPlayers) {
            player.bukkitPlayer.sendMessage(msg);
        }
    }

    public final void broadcastToTeam(String msg, Team team) {
        for (CPlayer player : team.getMembers()) {
            player.bukkitPlayer.sendMessage(msg);
        }
    }

    public final void message(String msg, Player player) {
        player.sendMessage(msg);
    }

    public final List<CPlayer> getAllPlayers() {
        return allPlayers;
    }

    public final List<Team> getTeams() {
        return teams;
    }

    public final String getName() {
        return name;
    }

    public final AbstractArena getCurrentArena() {
        return currentArena;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEvents(List<EventListener> events) {
        this.events = events;
    }

    public final void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public final void setCurrentArena(AbstractArena currentArena) {
        this.currentArena = currentArena;
    }

    public final void setAllPlayers(List<CPlayer> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public void onRegister() {
    }

    public void onEnd(Winnable winnable) {

    }

    public abstract void onStart();
}
