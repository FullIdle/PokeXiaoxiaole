package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.verapi;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import lombok.Getter;
import me.fullidle.ficore.ficore.common.bukkit.inventory.CraftItemStack;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.Data;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.IComparator;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.PapiHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

@Getter
public class V12Comparator implements IComparator<Pokemon> {
    private final Pokemon comparer;

    public V12Comparator(Pokemon comparer) {
        this.comparer = comparer;
    }

    @Override
    public boolean similar(IComparator<?> object) {
        if (object.getComparer() instanceof Pokemon){
            if (this.equals(object)) return true;
            Pokemon pokemon = ((V12Comparator) object).getComparer();
            return this.getComparer().equals(pokemon) ||
                    this.comparer.getSpecies() == pokemon.getSpecies() &&
                    this.comparer.isShiny() == pokemon.isShiny();
        }
        return false;
    }

    @Override
    public ItemStack getDisplay(Player player) {
        FileConfiguration config = Data.plugin.getConfig();
        ItemStack bukkitCopy = CraftItemStack.asBukkitCopy(ItemPixelmonSprite.getPhoto(this.comparer));
        ItemMeta itemMeta = bukkitCopy.getItemMeta();
        itemMeta.setDisplayName(PapiHelper.papi(player,config.getString("gui.pokeItemN").
                replace("{pokemon_name}",this.comparer.getLocalizedName())));
        itemMeta.setLore(Collections.singletonList(""));
        return bukkitCopy;
    }
}
