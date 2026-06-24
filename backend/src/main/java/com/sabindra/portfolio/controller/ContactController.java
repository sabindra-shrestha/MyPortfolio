package com.sabindra.portfolio.controller;

import com.sabindra.portfolio.entity.Contact;
import com.sabindra.portfolio.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contact")
@Data
@RequiredArgsConstructor
@CrossOrigin (origins = "*")
public class ContactController {

    private final ContactRepository contactRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public ResponseEntity<Contact> submitContact (@Valid @RequestBody Contact contact){
        //step 1: save to database(existing behavior)
        Contact saved = contactRepository.save(contact);

        //step 2: Publish event to kafka(new!)
        String message = "New Contact message from " + saved.getName() + " (" + saved.getEmail() + ")";
        kafkaTemplate.send("contact-event", message);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

}
