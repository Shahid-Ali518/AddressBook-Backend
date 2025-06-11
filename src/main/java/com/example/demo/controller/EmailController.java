package com.example.demo.controller;

import com.example.demo.entity.Email;
import com.example.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("send-mail")
    public void sendMail(@RequestBody Email email) {
        String to = email.getTo();
        String sub = email.getSubject();
        String body = email.getBody();

        emailService.sendMail(to, sub, body);
    }
}
