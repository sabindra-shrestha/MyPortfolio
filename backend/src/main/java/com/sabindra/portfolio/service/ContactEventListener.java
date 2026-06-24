package com.sabindra.portfolio.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class ContactEventListener {
    @KafkaListener(topics = "content-events", groupId ="portfolio-group")
    public void handleContactEvent(String message){
        System.out.println("[Contact] " + message);
        //In real app: send email, log to analytics, etc.
    }

    @KafkaListener(topics = "project-events", groupId ="portfolio-group")
    public void handleProjectEvent(String message){
        System.out.println("[Project] " + message);
    }

    @KafkaListener(topics = "blog-events", groupId ="portfolio-group")
    public void handleBlogEvent(String message){
        System.out.println("[Blog] " + message);
    }

}
