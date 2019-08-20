package com.credits.scapi.v2;

import java.util.Map;

public interface MapChangeListener<K, V> extends Map<K, V> {

    void onChanged(EntryChange<? extends K, ? extends V> entryChange);

    interface EntryChange<K, V> {

        K getKey();

        V getOldValue();

        V getNewValue();
    }
}
