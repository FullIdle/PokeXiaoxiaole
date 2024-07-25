package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        Data.plugin = this;
        reloadConfig();
    }

    @Override
    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
    }
}