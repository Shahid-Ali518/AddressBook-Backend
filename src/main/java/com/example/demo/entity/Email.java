package com.example.demo.entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Email {
    private String to;
    private String subject;
    private String body;
}
