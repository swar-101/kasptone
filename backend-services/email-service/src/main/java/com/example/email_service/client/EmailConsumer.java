package com.example.email_service.client;

import com.example.email_service.dto.MessageDTO;
import com.example.email_service.exception.EmailDeliveryException;
import com.example.email_service.util.EmailSessionUtil;
import com.example.email_service.util.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.Session;

import static com.example.email_service.util.Constants.EMAIL_SERVICE;
import static com.example.email_service.util.Constants.SIGNUP;

@Log4j2
@Component
public class EmailConsumer {

    private final ObjectMapper objectMapper;

    @Autowired
    public EmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Listens to the specified Kafka topic and sends an email based on the message received.
     *
     * @param message Message received from the Kafka Topic
     */
    @KafkaListener(topics = EMAIL_SERVICE, groupId = SIGNUP)
    public void sendEmail(String message) throws EmailDeliveryException {
        MessageDTO messageDTO = null;
        try {
            messageDTO = objectMapper.readValue(message, MessageDTO.class);
        } catch (JsonProcessingException e) {
            log.error("[EmailConsumer][sendEmail] Failed to convert message", e);
        }

        if (null == messageDTO) {
            log.error("[EmailConsumer][sendEmail] Message is null after JSON conversion");
            throw new EmailDeliveryException("MessageDTO is null after JSON conversion");
        }

        Session session = EmailSessionUtil.createGmailSession(messageDTO.getFrom(), "dummy password");

        try {
            EmailUtil.sendEmail(session, messageDTO.getTo(), messageDTO.getSubject(), messageDTO.getBody());
            log.info("[EmailConsumer][sendEmail] Email sent successfully to {}", messageDTO.getTo());
        } catch (Exception e) {
            log.error("[EmailConsumer][sendEmail] Failed to send email to {}", messageDTO, e);
            throw new EmailDeliveryException("Failed to send to {} due to: ", e);
        }
    }


}