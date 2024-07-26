package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.events;

import lombok.Getter;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.Gui;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PxxlEndEvent extends Event {
    @Getter public static HandlerList handlerList = new HandlerList();

    private final Gui gui;
    private final long timeCost;

    public PxxlEndEvent(Gui gui, long timeCost){
        this.gui = gui;
        this.timeCost = timeCost;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
