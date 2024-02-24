package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.events;

import lombok.Getter;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.AbHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class StartEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final AbHolder holder;


    public StartEvent(Player who, AbHolder holder) {
        super(who);
        this.holder = holder;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
