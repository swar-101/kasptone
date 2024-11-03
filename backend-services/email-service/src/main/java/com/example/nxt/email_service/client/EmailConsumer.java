package com.example.nxt.email_service.client;

import com.example.nxt.email_service.dto.MessageDTO;
import com.example.nxt.email_service.exception.EmailDeliveryException;
import com.example.nxt.email_service.service.EmailService;
import com.example.nxt.email_service.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log4j2
@Component
public class EmailConsumer {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Autowired
    public EmailConsumer(ObjectMapper objectMapper, EmailService emailService) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    /**
     * Listens to the specified Kafka topic and sends an email based on the message received.
     *
     * @param message Message received from the Kafka Topic
     */
    @KafkaListener(topics = Constants.EMAIL_SERVICE, groupId = Constants.SIGNUP)
    public void sendEmail(String message) throws EmailDeliveryException {
        MessageDTO messageDTO = deserialize(message);
        sendEmailToRecipient(messageDTO);
    }

    private MessageDTO deserialize(String message) throws EmailDeliveryException {
        try {
            return objectMapper.readValue(message, MessageDTO.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            log.error("[EmailConsumer][deserializeMessage] Failed to process the message: {}", message, e);
            throw new EmailDeliveryException("MessageDTO is Null after JSON conversion");
        }
    }

    private void sendEmailToRecipient(MessageDTO messageDTO) throws EmailDeliveryException {
        Optional.ofNullable(messageDTO)
                .orElseThrow(()-> new EmailDeliveryException("MessageDTO is null after JSON conversion"));

        try {
            emailService.sendEmail(messageDTO);
            log.info("[EmailConsumer][] Email sent successfully to {}", messageDTO.getTo());
        } catch (Exception e) {
            log.error("Failed to send email to {}", messageDTO.getTo());
            throw new EmailDeliveryException("Failed to send email due to ", e);
        }
    }
}