package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class HelpCmd extends ACmd{
    public HelpCmd(ACmd superCmd) {
        super(superCmd, "help");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
