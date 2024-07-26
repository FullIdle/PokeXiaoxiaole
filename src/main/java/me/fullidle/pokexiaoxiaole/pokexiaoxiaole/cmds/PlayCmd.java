package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.cmds;

import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.Data;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.Gui;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.IComparator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PlayCmd extends ACmd{
    public PlayCmd(ACmd superCmd) {
        super(superCmd, "play");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return false;
        }
        Player player = (Player) sender;
        Gui gui = new Gui(player,args.length > 0 ? args[0] : null);
        player.openInventory(gui.getInventory());
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return Collections.emptyList();
    }
}
