package me.fullidle.fipokestore.zdxxlextras;

import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.SomeMethod;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.events.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

import static me.fullidle.fipokestore.zdxxlextras.Main.main;

public class PlayerListener implements Listener {
    @EventHandler
    public void abandon(AbandonEvent e){
        String listKey = e.getHolder().listKey;
        List<String> cmd = SomeMethod.getConfigStringList(main.getConfig(),
                (listKey == null ? "runCmd" : "listRunCmd." + listKey) + ".abandon", e.getPlayer());
        Main.serverRunCmd(cmd);
    }
    @EventHandler
    public void clearUp(ClearUpEvent e){
        String listKey = e.getHolder().listKey;
        List<String> cmd = SomeMethod.getConfigStringList(main.getConfig(),
                (listKey == null ? "runCmd" : "listRunCmd." + listKey) + ".clearUp", e.getPlayer());
        Main.serverRunCmd(cmd);
    }
    @EventHandler
    public void pause(PauseEvent e){
    }
    @EventHandler
    public void start(StartEvent e){
        String listKey = e.getHolder().listKey;
        List<String> cmd = SomeMethod.getConfigStringList(main.getConfig(),
                (listKey == null ? "runCmd" : "listRunCmd." + listKey) + ".start", e.getPlayer());
        Main.serverRunCmd(cmd);
    }
    @EventHandler
    public void success(SuccessEvent e){
        String listKey = e.getHolder().listKey;
        List<String> cmd = SomeMethod.getConfigStringList(main.getConfig(),
                (listKey == null ? "runCmd" : "listRunCmd." + listKey) + ".success", e.getPlayer());
        Main.serverRunCmd(cmd);
    }
}
