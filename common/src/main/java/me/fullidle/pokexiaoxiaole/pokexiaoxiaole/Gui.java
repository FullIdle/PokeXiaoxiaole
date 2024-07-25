package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import lombok.Getter;
import me.fullidle.ficore.ficore.common.api.ineventory.ListenerInvHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Gui<T> extends ListenerInvHolder {
    private final Inventory inventory;
    private final Map<Integer,IComparator<T>> comparatorPosition = new HashMap<>();
    private IComparator<T> upObject;
    private int upSlot;

    public Gui(IComparator<T>[] layout) {
        if (layout.length != 54) {
            throw new RuntimeException("提供给消消乐gui用的消除对象[超出/不够]54");
        }

        this.inventory = Bukkit.createInventory(this,54,"  ");
        for (int i = 0; i < 54; i++) {
            this.inventory.setItem(i,getHideItemStack());
            this.comparatorPosition.put(i,layout[i]);
        }

        this.onClick((e)->{
            e.setCancelled(true);
            if (e.getClickedInventory() instanceof PlayerInventory) return;
            int slot = e.getSlot();
            if (slot > 0 && slot < 54 && this.upSlot != slot){
                this.upSlot = slot;
                IComparator<T> now = this.comparatorPosition.get(slot);
                if (this.upObject != null){
                    if (this.upObject.similar(now.getComparer())) {
                        this.inventory.setItem(this.upSlot,null);
                        this.inventory.setItem(slot,null);
                        this.upObject = null;
                        this.comparatorPosition.remove(this.upSlot);
                        this.comparatorPosition.remove(slot);
                        if (this.comparatorPosition.isEmpty()) {
                            e.getWhoClicked().closeInventory();
                            return;
                        }
                        return;
                    }
                    this.inventory.setItem(this.upSlot,getHideItemStack());
                }
                this.upObject = now;
                this.inventory.setItem(slot,now.getDisplay());
            }
        });
    }

    private ItemStack getHideItemStack(){
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
