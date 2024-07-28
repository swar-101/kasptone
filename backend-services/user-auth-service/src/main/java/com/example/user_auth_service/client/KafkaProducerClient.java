package com.example.user_auth_service.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class KafkaProducerClient {

    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducerClient(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        log.info("[KafkaProducerClient][sendMessage] Sign up message sent successfully to Email Service.");
    }
}