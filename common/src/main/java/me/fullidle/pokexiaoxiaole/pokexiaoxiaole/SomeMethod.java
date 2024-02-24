package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import me.clip.placeholderapi.PlaceholderAPI;
import me.fullidle.ficore.ficore.common.api.util.FileUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

public class SomeMethod {
    public static Plugin plugin;
    public static FileUtil playerData;

    public static String getConfigString(FileConfiguration config,String path, OfflinePlayer player){
        path = config.getString(path);
        return getFormatString(path,player);
    }
    public static String getFormatString(String msg,OfflinePlayer player){
        if (player != null){
            msg = PlaceholderAPI.setPlaceholders(player,msg);
        }
        return msg.replace("&","§");
    }
    public static List<String> getConfigStringList(FileConfiguration config,String path, OfflinePlayer player){
        return config.getStringList(path)
                .stream()
                .map(s->getFormatString(s,player))
                .collect(Collectors.toList());
    }
}
