package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.SomeMethod.*;

public class V12Method {
    public static ItemStack createPokemonItem(EnumSpecies enumSpecies, OfflinePlayer player) {
        Pokemon pokemon = Pixelmon.pokemonFactory.create(enumSpecies);
        ItemStack item = CraftItemStack.asBukkitCopy((net.minecraft.server.v1_12_R1.ItemStack)
                ((Object) ItemPixelmonSprite.getPhoto(
                        pokemon)));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getConfigString(plugin.getConfig(),"gui.pokeItemN",player).replace("{poke_name}",pokemon.getLocalizedName()));
        meta.setLore(getConfigStringList(plugin.getConfig(),"gui.pokeItemL",player));
        item.setItemMeta(meta);
        return item;
    }

    public static void randomPutItem(AbHolder holder,List<String> nameList){
        int pokeNumber = holder.getInventory().getSize() / 2;
        List<EnumSpecies> list = Arrays.asList(EnumSpecies.values());
        if (nameList != null){
            list = nameList.stream().map(EnumSpecies::getFromNameAnyCase).collect(Collectors.toList());
        }
        List<Integer> collect = IntStream.range(0, holder.getInventory().getSize()).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);

        Map<EnumSpecies,ItemStack> cache = new HashMap<>();
        for (int i = 0; i < pokeNumber; i++) {
            Collections.shuffle(list);
            EnumSpecies species = list.get(0);
            ItemStack item = cache.computeIfAbsent(species,k->createPokemonItem(species,holder.player));
            holder.itemStackMap.put(collect.get(i),item);
            holder.itemStackMap.put(collect.get(collect.size()-1-i),item);
        }
    }
}
