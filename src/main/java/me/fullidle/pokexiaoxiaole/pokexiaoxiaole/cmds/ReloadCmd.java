package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.cmds;

import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCmd extends ACmd{
    public ReloadCmd(ACmd superCmd) {
        super(superCmd, "reload");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Data.plugin.reloadConfig();
        sender.sendMessage("§aReloaded!");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return Collections.emptyList();
    }
}
