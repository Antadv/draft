package com.somelogs.javase.hashmap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 覆写 removeEldestEntry，实现缓存的效果
 * @author LBG - 2017/10/13 0013
 */
public class CacheLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private final static int MAX_CACHE_CAPACITY = 3;

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_CACHE_CAPACITY;
    }

    public static void main(String[] args) {
        CacheLinkedHashMap<String, String> map = new CacheLinkedHashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        map.put("c", "c");
        iterateMap(map);

        map.put("d", "d");
        iterateMap(map);
    }

    private static void iterateMap(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        }
        System.out.println("===============");
    }
}
