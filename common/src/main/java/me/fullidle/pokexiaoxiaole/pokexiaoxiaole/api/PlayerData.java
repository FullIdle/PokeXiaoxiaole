package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class PlayerData implements Listener {
    private final FileConfiguration config;
    private final File file;
    private final Map<String,Integer> xxlListRecord = new HashMap<>();
    @Getter
    private int defaultRecord;

    public PlayerData(File file,FileConfiguration config){
        this.file = file;
        this.config = config;
        this.defaultRecord = config.getInt("defaultRecord");
        ConfigurationSection section = config.getConfigurationSection("xxlListRecord");
        if (section == null) return;
        for (String key : section.getKeys(false)) {
            this.xxlListRecord.put(key,section.getInt(key));
        }
    }

    public int getXxlListRecord(String listName){
        return xxlListRecord.get(listName);
    }

    @SneakyThrows
    public int setDefaultRecord(int value){
        int old = getDefaultRecord();
        this.config.set("defaultRecord",value);
        this.config.save(this.file);
        this.defaultRecord = value;
        return old;
    }

    @SneakyThrows
    public int setXxListRecord(String listName, int value){
        int old = getXxlListRecord(listName);
        this.config.set("xxlListRecord."+listName,value);
        this.config.save(this.file);
        this.xxlListRecord.put(listName,value);
        return old;
    }




    private static File playerFolder;
    private static Map<OfflinePlayer, PlayerData> cache = new HashMap<>();

    public static void init(){
        cache.clear();
        playerFolder = new File(Data.plugin.getDataFolder(),"playerData");
        if (!playerFolder.exists()) {
            playerFolder.mkdirs();
        }
    }

    public static PlayerData getPlayerData(OfflinePlayer player){
        if (cache.containsKey(player)) return cache.get(player);
        File file = new File(playerFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration configuration;
        if (!file.exists()) {
            configuration = YamlConfiguration.loadConfiguration(new StringReader(" "));
        }else{
            configuration = YamlConfiguration.loadConfiguration(file);
        }
        PlayerData playerData = new PlayerData(file, configuration);
        cache.put(player,playerData);
        return playerData;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        cache.remove(((OfflinePlayer) e.getPlayer()));
    }
}
