package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PapiHelper {
    public static String papi(OfflinePlayer player,String str){
        return PlaceholderAPI.setPlaceholders(player,str.replace('&','§'));
    }
    
    public static List<String> papi(OfflinePlayer player, Collection<String> collection){
        ArrayList<String> list = new ArrayList<>(collection.size());
        for (String s : collection) {
            list.add(papi(player,s.replace('&','§')));
        }
        return list;
    }
}
