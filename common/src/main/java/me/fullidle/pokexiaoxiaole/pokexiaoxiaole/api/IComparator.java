package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IComparator<T> {
    boolean similar(IComparator<?> object);
    T getComparer();
    ItemStack getDisplay(Player player);
}
