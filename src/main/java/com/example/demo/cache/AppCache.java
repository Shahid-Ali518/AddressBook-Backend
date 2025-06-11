package com.example.demo.cache;

import com.example.demo.entity.ConfigEntity;
import com.example.demo.repository.AppConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;

// class to store data that used frequently in app and keep it in map
@Component
public class AppCache {

    public enum keys {
        country_api;
    }



    @Autowired
    private AppConfigRepository appConfigRepository;

    public HashMap<String, String> appCache;

    // this method is immediately called when bean is loaded
    @PostConstruct
    public void init(){
        appCache = new HashMap<>();

        List<ConfigEntity> all = appConfigRepository.findAll();
        for (ConfigEntity configEntity : all) {
            appCache.put(configEntity.getKey(), configEntity.getValue());
        }

    }
}
