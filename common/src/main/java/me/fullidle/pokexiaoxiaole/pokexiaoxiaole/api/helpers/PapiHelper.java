package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.helpers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PapiHelper {
    public static String papi(OfflinePlayer player,String str){
        return PlaceholderAPI.setPlaceholders(player,str);
    }
    public static List<String> papi(OfflinePlayer player, Collection<String> collection){
        return collection.stream().map(s->papi(player,s)).collect(Collectors.toList());
    }
}
