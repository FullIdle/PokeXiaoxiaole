package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.verapi;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.Data;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.IComparator;
import me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api.IVerApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class V12Api implements IVerApi {
    @Override
    public void init() {
        Data.iVerApi = this;
    }

    @Override
    public IComparator<? extends Object>[] getAllXxlComparatorList() {
        return getRandomComparators(EnumSpecies.values());
    }

    @Override
    public IComparator<?>[] getXxlComparatorList(Collection<String> pokeNames) {
        EnumSpecies[] optional = pokeNames.stream()
                .map(EnumSpecies::getFromNameAnyCase).toArray(EnumSpecies[]::new);
        return getRandomComparators(optional);
    }

    public IComparator<?>[] getRandomComparators(EnumSpecies[] optional) {
        List<V12Comparator> se = new ArrayList<>(54);
        for (int i = 0; i < 27; i++) {
            V12Comparator comparator = new V12Comparator(
                    Pixelmon.pokemonFactory.create(optional[Data.random.nextInt(optional.length)]));
            se.add(comparator);
            se.add(comparator);
        }
        Collections.shuffle(se);
        return se.toArray(new V12Comparator[0]);
    }
}
