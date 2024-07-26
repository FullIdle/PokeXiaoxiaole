package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.cmds;

import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.Collections;
import java.util.List;

public class MainCmd extends ACmd{
    public MainCmd() {
        super(null, "pokexiaoxiaole");
        new HelpCmd(this);
        new ReloadCmd(this);
        new PlayCmd(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            String la = args[0].toLowerCase();
            if (this.getSubCmdMap().containsKey(la)) {
                return this.getSubCmdMap().get(la).onCommand(sender, cmd, label, rOArgs(args));
            }
        }
        this.getSubCmdMap().get("help").onCommand(sender, cmd, label, args);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return Collections.emptyList();
    }

    public void register(Main plugin){
        PluginCommand command = plugin.getCommand(this.getName());
        command.setExecutor(this);
        command.setTabCompleter(this);
    }
}
