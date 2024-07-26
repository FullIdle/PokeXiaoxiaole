package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api;

import lombok.Getter;
import me.fullidle.ficore.ficore.common.api.ineventory.ListenerInvHolder;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.events.PxxlEndEvent;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.events.PxxlStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Gui extends ListenerInvHolder {
    private final Inventory inventory;
    private final Map<Integer, IComparator<?>> comparatorPosition = new HashMap<>();
    private IComparator<?> upObject;
    private int upSlot = -999;
    private final Player player;
    private LocalDateTime startTime;
    private final String xxlListName;

    public Gui(Player player,String xxlListName) {
        FileConfiguration config = Data.plugin.getConfig();
        this.xxlListName = xxlListName;
        IComparator<?>[] layout = null;
        if (this.xxlListName != null){
            List<String> list = Data.plugin.getConfig().getStringList("xxl-list." + this.xxlListName);
            if (!list.isEmpty()) {
                layout = Data.iVerApi.getXxlComparatorList(list);
            }
        }
        if (layout == null) {
            layout = Data.iVerApi.getAllXxlComparatorList();
        }

        if (layout.length != 54) {
            throw new RuntimeException("提供给消消乐gui用的消除对象[超出/不够]54");
        }

        this.player = player;
        this.inventory = Bukkit.createInventory(this, 54,PapiHelper.papi(this.player,config.getString("gui.title")));
        for (int i = 0; i < layout.length; i++) {
            this.inventory.setItem(i, getHideItemStack());
            this.comparatorPosition.put(i, layout[i]);
        }

        this.onOpen(e->{
            this.startTime = LocalDateTime.now();
            Bukkit.getPluginManager().callEvent(new PxxlStartEvent(this));
        });

        this.onClick((e) -> {
            e.setCancelled(true);
            if (e.getClickedInventory() instanceof PlayerInventory) return;
            int slot = e.getSlot();
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
            if (slot > -1 && slot < 54 && this.upSlot != slot) {
                IComparator<?> now = this.comparatorPosition.get(slot);
                if (this.upObject != null) {
                    if (this.upObject.similar(now)) {
                        this.inventory.setItem(this.upSlot, null);
                        this.inventory.setItem(slot, null);
                        this.upObject = null;
                        this.comparatorPosition.remove(this.upSlot);
                        this.comparatorPosition.remove(slot);
                        this.upSlot = -999;
                        if (this.comparatorPosition.isEmpty()) {
                            e.getWhoClicked().closeInventory();
                            return;
                        }
                        return;
                    }
                    this.inventory.setItem(this.upSlot, getHideItemStack());
                }
                this.upSlot = slot;
                this.upObject = now;
                this.inventory.setItem(slot, now.getDisplay(this.player));
            }
        });

        this.onClose(e -> {
            long seconds;
            if (this.comparatorPosition.isEmpty()){
                //完成
                LocalDateTime now = LocalDateTime.now();
                seconds = Duration.between(this.startTime, now).getSeconds();
                this.player.sendMessage(seconds+"");
            }else{
                //放弃
                this.player.sendMessage("abandon");
                seconds = 0;
            }
            Bukkit.getPluginManager().callEvent(new PxxlEndEvent(this,seconds));
        });
    }

    private ItemStack getHideItemStack() {
        FileConfiguration config = Data.plugin.getConfig();
        ItemStack itemStack = new ItemStack(Material.getMaterial(config.getString("gui.hideItemM")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemMeta.setLore(Collections.singletonList(""));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
