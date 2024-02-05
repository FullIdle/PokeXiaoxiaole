package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends JavaPlugin {
    public static Main plugin;
    public static String[] help;
    @Override
    public void onEnable() {
        plugin = this;
        reloadConfig();
        getCommand(plugin.getDescription().getName().toLowerCase()).setExecutor(this);
        getLogger().info("§aPlugin is enabled!");
    }

    @Override
    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
        help = getConfigStringList("Msg.help",null).toArray(new String[0]);
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
                if (playerHandlerRequired(sender,cmd)){
                    return false;
                }
            }
        }
        sender.sendMessage(help);
        return false;
    }

    public boolean playerHandlerRequired(CommandSender sender,String cmd){
        if (!(sender instanceof Player)){
            sender.sendMessage("§cYou are not a player!");
            return true;
        }
        Player player = (Player) sender;
        switch (cmd){
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
                XiaoxiaoleHolder.BeginResults begin = XiaoxiaoleHolder.begin(player);
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
        return null;
    }

    public static String getConfigString(String path, OfflinePlayer player){
        path = plugin.getConfig().getString(path);
        return getFormatString(path,player);
    }
    public static String getFormatString(String msg,OfflinePlayer player){
        if (player != null){
            msg = PlaceholderAPI.setPlaceholders(player,msg);
        }
        return msg.replace("&","§");
    }
    public static List<String> getConfigStringList(String path,OfflinePlayer player){
        return plugin.getConfig().getStringList(path)
                .stream()
                .map(s->getFormatString(s,player))
                .collect(Collectors.toList());
    }

    public static void serverRunCmd(List<String> cmdList){
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        for (String s : cmdList) {
            Bukkit.dispatchCommand(sender,s);
        }
    }
}