package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.events;

import lombok.Getter;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.AbHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

@Getter
public class ClearUpEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;
    private final ItemStack upItem;
    private final ItemStack thisItem;
    private final AbHolder holder;

    public ClearUpEvent(Player player, ItemStack upItem, ItemStack thisItem,AbHolder holder) {
        super(player);
        this.upItem = upItem;
        this.thisItem = thisItem;
        this.holder = holder;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
