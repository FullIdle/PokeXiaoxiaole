package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.PlayerData;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Papi extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "pxxl";
    }

    @Override
    public @NotNull String getAuthor() {
        return "GSQ_FI";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    /*pxxl_record_[表名【可选】]*/
    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        String[] split = params.split("_");
        if (split[0].equalsIgnoreCase("record")) {
            PlayerData data = PlayerData.getPlayerData(player);
            if (split.length > 1) {
                return String.valueOf(data.getXxlListRecord(split[1]));
            }
            return String.valueOf(data.getDefaultRecord());
        }
        return "ERROR";
    }
}
