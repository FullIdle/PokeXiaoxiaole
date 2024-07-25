package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import lombok.Getter;
import me.fullidle.ficore.ficore.common.bukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

@Getter
public class V12IComparator implements IComparator<Pokemon> {
    private final Pokemon comparer;

    public V12IComparator(Pokemon comparer) {
        this.comparer = comparer;
    }

    @Override
    public boolean similar(Pokemon object) {
        return this.comparer.getSpecies() == object.getSpecies() &&
                this.comparer.isShiny() == object.isShiny();
    }

    @Override
    public ItemStack getDisplay() {
        net.minecraft.item.ItemStack photo = ItemPixelmonSprite.getPhoto(this.comparer);
        return CraftItemStack.asBukkitCopy(photo);
    }
}
