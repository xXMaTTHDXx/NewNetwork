package com.viperpvp.core.game.kit;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Matt on 02/09/2016.
 */
public abstract class Kit {

    private String name;
    private List<ItemStack> items;
    private int price;

    public Kit(String name, List<ItemStack> items, int price) {
        this.name = name;
        this.items = items;
        this.price = price;
    }

    public abstract void apply();
}
