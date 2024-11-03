package com.nxt.payment_service.exception;

public class PaymentProcessingException extends RuntimeException {

    public PaymentProcessingException() { super("Something went wrong while processing the payment."); }

    public PaymentProcessingException(String message) { super(message); }

    public PaymentProcessingException(String message, Throwable cause) { super(message, cause); }
}