package com.apicatalog.jsonld.context.cache;

import java.util.function.BiConsumer;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

public final class ConcurrentLruCache<K, V> implements Cache<K, V> {

    private final int maxCapacity;
    private final Object2ObjectLinkedOpenHashMap<K, V> cache;
    private final ObjectLinkedOpenHashSet<K> accessOrderSet;

    public ConcurrentLruCache(final int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.cache = new Object2ObjectLinkedOpenHashMap<>((int)(maxCapacity / 0.75 + 1));
        this.accessOrderSet = new ObjectLinkedOpenHashSet<>((int)(maxCapacity / 0.75 + 1));
    }

    @Override
    public synchronized boolean containsKey(final K key) {
        return cache.containsKey(key);
    }

    @Override
    public synchronized V get(final K key) {
        V value = cache.get(key);
        if (value != null) {
            // Move the key to the end of the access order
            accessOrderSet.remove(key);
            accessOrderSet.add(key);
        }
        return value;
    }

    @Override
    public synchronized void put(final K key, V value) {
        if (this.size() > maxCapacity) {
            K eldestKey = cache.keySet().iterator().next();
            cache.remove(eldestKey);
            accessOrderSet.remove(eldestKey);
        }
        cache.put(key, value);
        // Add the key to the end of the access order
        accessOrderSet.add(key);
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        // Iterate over the keys in access order
        accessOrderSet.forEach(key -> action.accept(key, cache.get(key)));
    }

    public synchronized long size() {
        return cache.size();
    }
}
