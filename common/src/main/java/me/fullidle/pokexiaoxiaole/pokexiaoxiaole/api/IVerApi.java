package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.api;

import java.util.Collection;

public interface IVerApi {
    void init();
    IComparator<?>[] getAllXxlComparatorList();
    IComparator<?>[] getXxlComparatorList(Collection<String> pokeNames);
}
