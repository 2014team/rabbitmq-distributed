package com.mashibing.util;

import java.util.HashMap;
import java.util.Map;

public class GlobalCache {

    private static Map map = new HashMap();

    public static void set(String key,Object value){
        map.put(key,value);
    }

    public static Object get(String key){
        Object value = map.get(key);
        return value;
    }

    public static void remove(String key){
        map.remove(key);
    }

}