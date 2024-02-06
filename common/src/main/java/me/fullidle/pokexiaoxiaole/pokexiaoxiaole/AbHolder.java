package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import me.fullidle.ficore.ficore.common.api.ineventory.ListenerInvHolder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbHolder extends ListenerInvHolder {
    public final Player player;
    public final Map<Integer, ItemStack> itemStackMap = new HashMap<>();
    public static Map<UUID, Inventory> cacheInv = new HashMap<>();
    public Inventory inv;
    public Pair<Integer,ItemStack> upItem;
    public ItemStack hideStack;
    public long lastTime = 0L;
    public long timeCost;
    protected AbHolder(Player player) {
        this.player = player;
    }
}
