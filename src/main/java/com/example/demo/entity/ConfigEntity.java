package com.example.demo.entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Data
@Document(collection = "config_add_book")
public class ConfigEntity {

    private String key;
    private String value;
}
