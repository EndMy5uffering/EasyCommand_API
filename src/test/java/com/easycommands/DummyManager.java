package com.easycommands;

import java.util.HashMap;
import java.util.Map;

public class DummyManager {
    public Map<String, Object> keyToObject = new HashMap<>();

    public void add(String key, Object obj){
        this.keyToObject.put(key, obj);
    }

    public boolean has(String key){
        return this.keyToObject.get(key) != null;
    }

    public Object get(String key){
        return this.keyToObject.get(key);
    }

}
