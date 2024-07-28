package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import me.fullidle.ficore.ficore.common.SomeMethod;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.Data;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.PlayerData;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.cmds.MainCmd;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.verapi.V12Api;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onLoad() {
        if (SomeMethod.getMinecraftVersion().equals("1.12.2")) {
            new V12Api().init();
        }
        if (SomeMethod.getMinecraftVersion().equals("1.16.5")){
        }
    }

    @Override
    public void onEnable() {
        Data.plugin = this;
        reloadConfig();
        new MainCmd().register(this);
        new Papi().register();
    }

    @Override
    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
        PlayerData.init();
    }
}