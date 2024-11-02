package com.example.nxt.email_service.exception;

public class EmailDeliveryException extends Exception {

    public EmailDeliveryException(String message) {
        super(message);
    }

    public EmailDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
