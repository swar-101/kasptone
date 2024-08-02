package com.example.email_service.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

    public static void sendEmail(Session session, String RecipientEmail, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(session);

            message.addHeader(HttpHeaders.CONTENT_TYPE, "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
        }
        catch (Exception e) {

        }
    }
}
