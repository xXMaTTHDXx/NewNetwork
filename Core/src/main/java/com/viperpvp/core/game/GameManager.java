package com.viperpvp.core.game;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Matt on 23/08/2016.
 */
public class GameManager {

    private List<Class<? extends Minigame>> registeredGames = new ArrayList<>();

    private static GameManager instance;

    public static GameManager getInstance() {
        return instance;
    }

    public Class<? extends Minigame> getMinigame(String name) {
        for (Class<? extends Minigame> mini : registeredGames) {
            if (mini.getAnnotation(MinigameMeta.class).name().equalsIgnoreCase(name)) {
                return mini;
            }
        }
        return null;
    }

    public void registerGame(Class<? extends Minigame> clazz) {
        Validate.notNull(clazz.getAnnotation(MinigameMeta.class));
        Validate.isTrue(!registeredGames.contains(clazz));

        MinigameMeta meta = clazz.getAnnotation(MinigameMeta.class);


        System.out.println("Registering Game " + meta.name() + ". Created by: " + Arrays.toString(meta.authors()));



        this.registeredGames.add(clazz);
    }
}
