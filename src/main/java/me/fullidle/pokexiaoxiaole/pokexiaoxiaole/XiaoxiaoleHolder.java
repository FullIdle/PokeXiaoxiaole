package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import javafx.util.Pair;
import me.fullidle.ficore.ficore.common.api.ineventory.ListenerInvHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.Main.*;
import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.XiaoxiaoleHolder.BeginResults.*;

public class XiaoxiaoleHolder extends ListenerInvHolder {
    public static Map<UUID, Inventory> cacheInv = new HashMap<>();
    private final Inventory inv;
    private final Map<Integer,ItemStack> itemStackMap = new HashMap<>();
    private Pair<Integer,ItemStack> upItem;
    private final ItemStack hideStack;
    private final Player player;

    private XiaoxiaoleHolder(Player player){
        this.player = player;
        {
            hideStack = new ItemStack(Material.getMaterial(getConfigString("gui.hideItemM",player)));
            ItemMeta meta = hideStack.getItemMeta();
            meta.setDisplayName(" ");
            hideStack.setItemMeta(meta);
        }
        inv = Bukkit.createInventory(this,6*9,getConfigString("gui.title",player));
        initItem();
        initEventHandler();
    }

    public void initItem(){
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i,hideStack);
        }

        int pokeNumber = inv.getSize() / 2;
        List<EnumSpecies> list = Arrays.asList(EnumSpecies.values());
        List<Integer> collect = IntStream.range(0, inv.getSize()).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);
        for (int i = 0; i < pokeNumber; i++) {
            Collections.shuffle(list);
            EnumSpecies species = list.get(0);
            ItemStack item = createPokemonItem(species);
            itemStackMap.put(collect.get(i),item);
            itemStackMap.put(collect.get(collect.size()-1-i),item);
        }
    }

    public void initEventHandler(){
        onClose(e->{
            if (itemStackMap.isEmpty()) {
                return;
            }
            Inventory inventory = e.getInventory();
            Player p = (Player) e.getPlayer();
            cacheInv.put(p.getUniqueId(),inventory);
            p.sendMessage(getConfigString("Msg.leaveTemporarily",p));
        });
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
                    inv.setItem(nowSlot, null);
                    inv.setItem(upSlot, null);
                    itemStackMap.remove(nowSlot);
                    itemStackMap.remove(upSlot);
                    upItem = null;
                    System.out.println(itemStackMap.size());
                    if (itemStackMap.isEmpty()){
                        HumanEntity human = e.getWhoClicked();
                        human.closeInventory();
                        serverRunCmd(getConfigStringList("runCmd.successe",player));
                        human.sendMessage(getConfigString("Msg.completeXioxiaole",player));
                    }
                    return;
                }
                inv.setItem(upItem.getKey(),hideStack);
            }
            upItem = new Pair<>(nowSlot,closetItem);
            inv.setItem(nowSlot,closetItem);
        });
        onDrag(e->{
            e.setCancelled(true);
        });
    }

    private ItemStack createPokemonItem(EnumSpecies enumSpecies) {
        Pokemon pokemon = Pixelmon.pokemonFactory.create(enumSpecies);
        ItemStack item = CraftItemStack.asBukkitCopy((net.minecraft.server.v1_12_R1.ItemStack)
                ((Object) ItemPixelmonSprite.getPhoto(
                        pokemon)));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getConfigString("gui.pokeItemN",player).replace("{poke_name}",pokemon.getLocalizedName()));
        meta.setLore(getConfigStringList("gui.pokeItemL",player));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public static BeginResults begin(Player player){
        UUID uuid = player.getUniqueId();
        boolean b = cacheInv.containsKey(uuid);
        Inventory inventory = b ?cacheInv.get(uuid):new XiaoxiaoleHolder(player).getInventory();
        player.openInventory(inventory);
        return b?RESUME:START;
    }
    public static boolean stop(Player player){
        UUID uuid = player.getUniqueId();
        if (cacheInv.containsKey(uuid)){
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
