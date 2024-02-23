package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.SomeMethod.getConfigString;
import static me.fullidle.pokexiaoxiaole.pokexiaoxiaole.SomeMethod.getConfigStringList;

public class V16Method {
    public static ItemStack createPokemonItem(Species enumSpecies, OfflinePlayer player) {
        Pokemon pokemon = PokemonFactory.create(enumSpecies);
        ItemStack item = CraftItemStack.asBukkitCopy(SpriteItemHelper.getPhoto(pokemon));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getConfigString("gui.pokeItemN",player).replace("{poke_name}",pokemon.getLocalizedName()));
        meta.setLore(getConfigStringList("gui.pokeItemL",player));
        item.setItemMeta(meta);
        return item;
    }

    public static void randomPutItem(AbHolder holder,List<String> nameList){
        int pokeNumber = holder.getInventory().getSize() / 2;
        List<Species> list = PixelmonSpecies.getAll();
        if (nameList != null) {
            list = nameList.stream().map(s-> PixelmonSpecies.get(s).get().getValueUnsafe()).collect(Collectors.toList());
        }
        List<Integer> collect = IntStream.range(0, holder.getInventory().getSize()).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);
        Map<Species,ItemStack> cache = new HashMap<>();
        for (int i = 0; i < pokeNumber; i++) {
            Collections.shuffle(list);
            Species species = list.get(0);
            ItemStack item = cache.computeIfAbsent(species,k->createPokemonItem(species,holder.player));
            holder.itemStackMap.put(collect.get(i),item);
            holder.itemStackMap.put(collect.get(collect.size()-1-i),item);
        }
    }
}
