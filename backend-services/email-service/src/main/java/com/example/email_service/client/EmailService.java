package com.example.email_service.client;

import com.example.email_service.dto.MessageDTO;
import com.example.email_service.util.EmailSessionUtil;
import com.example.email_service.util.EmailUtil;
import org.springframework.stereotype.Service;

import javax.mail.Session;

@Service
public class EmailService {


    public void sendEmail(MessageDTO messageDTO) {
        Session session = EmailSessionUtil.createGmailSession(messageDTO.getFrom(),"dummy password");
        EmailUtil.sendEmail(session, messageDTO.getTo(), messageDTO.getSubject(), messageDTO.getBody());
    }
}
