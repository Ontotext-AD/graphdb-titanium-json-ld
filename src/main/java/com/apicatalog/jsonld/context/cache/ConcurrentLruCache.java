package com.apicatalog.jsonld.context.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ConcurrentLruCache<K, V> implements Cache<K, V> {

    private final Map<K, V> cache;

    public ConcurrentLruCache(final int maxCapacity) {
        this.cache = new LinkedHashMap<K, V>((int)(maxCapacity / 0.75 + 1), 0.75f, true) {

            private static final long serialVersionUID = 4822962879473741809L;

            @Override
            protected synchronized boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return this.size() > maxCapacity;
            }
        };
    }

    @Override
    public synchronized boolean containsKey(final K key) {
        return cache.containsKey(key);
    }

    @Override
    public synchronized V get(final K key) {
        return cache.get(key);
    }

    @Override
    public synchronized void put(final K key, V value) {
        cache.put(key, value);
    }

    public synchronized long size() {
        return cache.size();
    }
}
