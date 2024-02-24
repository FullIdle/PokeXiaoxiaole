package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import lombok.Getter;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

import static me.fullidle.ficore.ficore.common.SomeMethod.getMinecraftVersion;
import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.SomeMethod.*;
import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.XiaoxiaoleHolder.BeginResults.RESUME;
import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.XiaoxiaoleHolder.BeginResults.START;

@Getter
public class XiaoxiaoleHolder extends AbHolder{
    private XiaoxiaoleHolder(Player player, List<String> nameList,String listKey){
        super(player,nameList,listKey);
        {
            hideStack = new ItemStack(Material.getMaterial(getConfigString(plugin.getConfig(),"gui.hideItemM",player)));
            ItemMeta meta = hideStack.getItemMeta();
            meta.setDisplayName(" ");
            hideStack.setItemMeta(meta);
        }
        inv = Bukkit.createInventory(this,6*9,getConfigString(plugin.getConfig(),"gui.title",player));
        initItem();
        initEventHandler();
    }

    public void initItem(){
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i,hideStack);
        }

        if (getMinecraftVersion().contains("1.12.2")){
            V12Method.randomPutItem(this,nameList);
            return;
        }
        V16Method.randomPutItem(this,nameList);
    }

    public void initEventHandler(){
        onOpen(e-> lastTime = System.currentTimeMillis());
        onClose(e-> Bukkit.getScheduler().runTask(plugin,()->{
            Bukkit.getPluginManager().callEvent(new PauseEvent(player,this));

            long now = System.currentTimeMillis();
            timeCost = timeCost+(now-lastTime);
            if (itemStackMap.values().isEmpty()){
                stop(player);
                player.sendMessage("§3It takes you §a"+(double) timeCost / 1000+"§3s to complete the game");
                saveTheFastestRecord();
                return;
            }
            Player p = (Player) e.getPlayer();
            cacheInv.put(p.getUniqueId(),inv);
            p.sendMessage(getConfigString(plugin.getConfig(),"Msg.leaveTemporarily",p));
        }));
        onClick(e->{
            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            if (item == null||item.getType().equals(Material.AIR)) {
                return;
            }
            int nowSlot = e.getSlot();
            if (!itemStackMap.containsKey(nowSlot)) {
                return;
            }
            ItemStack closetItem = itemStackMap.get(nowSlot);
            if (upItem != null){
                int upSlot = upItem.getKey();
                if (nowSlot == upSlot){
                    return;
                }
                if (upItem.getValue() == closetItem) {
                    //触发消除事件
                    ClearUpEvent event = new ClearUpEvent(player,upItem.getValue(),closetItem,this);
                    Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return;
                    }

                    upItem = null;
                    inv.setItem(nowSlot, null);
                    inv.setItem(upSlot, null);
                    itemStackMap.remove(nowSlot);
                    itemStackMap.remove(upSlot);

                    if (itemStackMap.values().isEmpty()){
                        player.closeInventory();

                        Bukkit.getPluginManager().callEvent(new SuccessEvent(player,this));

                        player.sendMessage(getConfigString(plugin.getConfig(),"Msg.completeXioxiaole",player));
                    }
                    return;
                }
                inv.setItem(upItem.getKey(),hideStack);
            }
            upItem = new Pair<>(nowSlot,closetItem);
            inv.setItem(nowSlot,closetItem);
        });
        onDrag(e-> e.setCancelled(true));
    }

    private void saveTheFastestRecord() {
        FileConfiguration config = playerData.getConfiguration();
        double old = config.getDouble(player.getName(),99999.0);
        double newC = Math.min(old, ((double) timeCost / 1000));
        config.set(player.getName(),newC);
        playerData.save();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public static BeginResults begin(Player player,List<String> nameList,String listKey){
        UUID uuid = player.getUniqueId();
        boolean b = cacheInv.containsKey(uuid);
        XiaoxiaoleHolder holder = new XiaoxiaoleHolder(player, nameList,listKey);
        Inventory inventory = b ?cacheInv.get(uuid): holder.getInventory();
        player.openInventory(inventory);
        if (!b){
            Bukkit.getPluginManager().callEvent(new StartEvent(player,holder));
        }
        return b?RESUME:START;
    }
    public static boolean stop(Player player){
        UUID uuid = player.getUniqueId();
        if (cacheInv.containsKey(uuid)){
            XiaoxiaoleHolder holder = (XiaoxiaoleHolder) cacheInv.get(uuid).getHolder();
            Bukkit.getPluginManager().callEvent(new AbandonEvent(player,holder));
            cacheInv.remove(uuid);
            return true;
        }
        return false;
    }
    public enum BeginResults{
        RESUME,
        START
    }
}
