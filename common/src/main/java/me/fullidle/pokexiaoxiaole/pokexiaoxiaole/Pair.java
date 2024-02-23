package me.fullidle.pokexiaoxiaole.pokexiaoxiaole;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Pair<K, V> implements Serializable {
    private final K key;
    private final V value;

    public Pair(K key,V value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        return this.key + "=" + this.value;
    }

    public int hashCode() {
        return this.key.hashCode() * 13 + (this.value == null ? 0 : this.value.hashCode());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Pair)) {
            return false;
        } else {
            Pair pair = (Pair)o;
            if (this.key != null) {
                if (!this.key.equals(pair.key)) {
                    return false;
                }
            } else if (pair.key != null) {
                return false;
            }

            if (this.value != null) {
                return this.value.equals(pair.value);
            } else return pair.value == null;
        }
    }
}