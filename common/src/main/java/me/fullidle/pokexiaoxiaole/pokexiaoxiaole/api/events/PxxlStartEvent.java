package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.events;

import lombok.Getter;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.Gui;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PxxlStartEvent extends Event {
    @Getter public static HandlerList handlerList = new HandlerList();

    private final Gui gui;

    public PxxlStartEvent(Gui gui){
        this.gui = gui;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
