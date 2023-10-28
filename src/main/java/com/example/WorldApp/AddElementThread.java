package com.example.WorldApp;

import java.util.Map;

public class AddElementThread extends Thread{
    private Map<String, String> map;
    private String key;
    private String elem;

    public AddElementThread(Map<String, String> map, String key, String elem) {
        this.map = map;
        this.key = key;
        this.elem = elem;
    }

    @Override
    public void run() {
        synchronized (map) {
            map.put(key, elem);
        }
    }
}
