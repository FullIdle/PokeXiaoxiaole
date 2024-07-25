package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import org.bukkit.inventory.ItemStack;

public interface IComparator<T> {
    boolean similar(T object);
    T getComparer();
    ItemStack getDisplay();
}
