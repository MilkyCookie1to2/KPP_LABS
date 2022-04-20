package com.bsuir.kpp;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class NumbersCache {
    private final Map<Integer, GeneratedNumber> hashMapLess = new LinkedHashMap<>();
    private final Map<Integer, GeneratedNumber> hashMapMore = new LinkedHashMap<>();

    public boolean containsKeyInHashMapMore(Integer key) {
        return hashMapMore.containsKey(key);
    }

    public boolean containsKeyInHashMapLess(Integer key) {
        return hashMapLess.containsKey(key);
    }

    public void addToMapMore(Integer key, GeneratedNumber result) {
        hashMapMore.put(key, result);
    }

    public void addToMapLess(Integer key, GeneratedNumber result) {
        hashMapLess.put(key, result);
    }

    public GeneratedNumber getParametersMore(Integer key) {
        return hashMapMore.get(key);
    }

    public GeneratedNumber getParametersLess(Integer key) {
        return hashMapLess.get(key);
    }

    public Map<Integer, GeneratedNumber> getCacheMore(){
        return hashMapMore;
    }

    public Map<Integer, GeneratedNumber> getCacheLess(){
        return hashMapLess;
    }
}
