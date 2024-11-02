package com.nxt.payment_service.payment;

public interface PaymentGateway {
    /**
     * @param amount
     * @param orderId
     * @param phoneNumber
     * @param name
     * @return
     */
    String getPaymentLink(Long amount, String orderId, String phoneNumber, String name);
}