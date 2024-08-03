package com.example.email_service.service;

import com.example.email_service.dto.MessageDTO;
import com.example.email_service.exception.EmailDeliveryException;
import com.example.email_service.util.EmailSessionUtil;
import com.example.email_service.util.EmailUtil;
import org.springframework.stereotype.Service;

import javax.mail.Session;

@Service
public class EmailService {

    /**
     * Sends an email using the provided {@link MessageDTO} object.
     *
     * @param messageDTO the {@link MessageDTO} object containing details such as sender's email, recipient's email,
     *                   subject and body.
     * @throws EmailDeliveryException if there is an error while sending the email.
     */
    public void sendEmail(MessageDTO messageDTO) throws EmailDeliveryException {
        Session session = EmailSessionUtil.createGmailSession(messageDTO.getFrom(), "dummy password");
        EmailUtil.sendEmail(session, messageDTO.getTo(), messageDTO.getSubject(), messageDTO.getBody());
    }
}