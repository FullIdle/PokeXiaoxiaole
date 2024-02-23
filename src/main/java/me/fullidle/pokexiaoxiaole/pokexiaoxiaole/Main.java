package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import me.fullidle.ficore.ficore.common.api.util.FileUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.SomeMethod.*;

public class Main extends JavaPlugin {
    public static String[] help;
    @Override
    public void onEnable() {
        plugin = this;
        reloadConfig();
        new MyPapi().register();
        getCommand(plugin.getDescription().getName().toLowerCase()).setExecutor(this);
        getLogger().info("§aPlugin is enabled!");
    }

    @Override
    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
        help = getConfigStringList("Msg.help",null).toArray(new String[0]);

        playerData = FileUtil.getInstance(new File(getDataFolder(), "playerData.yml"), true);
    }

    private static final List<String> subCmd = Arrays.asList(
            "help",
            "reload",
            "stop",
            "begin"
    );
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1){
            String cmd = args[0].toLowerCase();
            if (subCmd.contains(cmd)){
                if (cmd.equalsIgnoreCase("reload")){
                    reloadConfig();
                    sender.sendMessage(getConfigString("Msg.reload",null));
                    return false;
                }
                if (playerHandlerRequired(sender,args)){
                    return false;
                }
            }
        }
        sender.sendMessage(help);
        return false;
    }

    public boolean playerHandlerRequired(CommandSender sender,String[] args){
        if (!(sender instanceof Player)){
            sender.sendMessage("§cYou are not a player!");
            return true;
        }
        Player player = (Player) sender;
        switch (args[0]){
            case "stop":{
                boolean b = XiaoxiaoleHolder.stop(player);
                String msg = b?getConfigString("Msg.stopGame",player):"§cOn null Object";
                sender.sendMessage(msg);
                if (b){
                    serverRunCmd(getConfigStringList("runCmd.abandon",player));
                }
                return true;
            }
            case "begin":{
                List<String> nameList = args.length > 1?getConfig().getStringList("nameList."+args[1]):null;
                XiaoxiaoleHolder.BeginResults begin = XiaoxiaoleHolder.begin(player,nameList);
                boolean b = begin == XiaoxiaoleHolder.BeginResults.START;
                String msg = b ?getConfigString("Msg.startGame",player):getConfigString("Msg.resumeGame",player);
                sender.sendMessage(msg);
                if (b){
                    serverRunCmd(getConfigStringList("runCmd.start",player));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length < 1) return subCmd;
        if (args.length == 1) return subCmd.stream().filter(s->s.startsWith(args[0])).collect(Collectors.toList());
        if (args[0].equalsIgnoreCase("begin")) {
            return getConfig().getConfigurationSection("nameList").getKeys(false).stream().filter(s->s.startsWith(args[1])).collect(Collectors.toList());
        }
        return null;
    }
}