package com.example.demo.scheduler;
import com.example.demo.cache.AppCache;
import com.example.demo.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AppScheduler {

    @Autowired
    private AppCache appCache;

    @Autowired
    private EmailService emailService;

    // this is method is executed after every five mints
    @Scheduled(cron = "*/5 * * * * *") // cron expression every 5 mints
    public void clearAppCache(){
        appCache.init();
    }


    // this mail is send in every mint automaticall

//    @Scheduled(cron="* * * * * *")
    public void sendMail(){
        String to = "sa4546630@gmail.com";
        String sub = "Notification From Spring Scheduler";
        String body = "Dear Customer! Hope you are doing well, this is mail from spring project";
        emailService.sendMail(to, sub, body);
    }
}
