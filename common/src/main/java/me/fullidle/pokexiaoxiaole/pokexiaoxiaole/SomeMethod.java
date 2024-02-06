package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import me.clip.placeholderapi.PlaceholderAPI;
import me.fullidle.ficore.ficore.common.api.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

public class SomeMethod {
    public static Plugin plugin;
    public static FileUtil playerData;

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
    public static List<String> getConfigStringList(String path, OfflinePlayer player){
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
