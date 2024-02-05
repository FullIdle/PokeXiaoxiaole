package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.Main.playerData;
import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.Main.plugin;

public class MyPapi extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return plugin.getDescription().getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        return "FullIlde";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.split("_")[0].equalsIgnoreCase("record")) {
            return String.valueOf(playerData.getConfiguration().getDouble(player.getName()));
        }
        return null;
    }
}
