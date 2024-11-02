package com.nxt.payment_service.payment.stripe;

import com.nxt.payment_service.payment.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements PaymentGateway {
    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNumber, String name) {
        return "";
    }
}