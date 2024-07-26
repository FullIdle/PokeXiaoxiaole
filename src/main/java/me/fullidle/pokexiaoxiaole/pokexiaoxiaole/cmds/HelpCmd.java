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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return Collections.emptyList();
    }
}
