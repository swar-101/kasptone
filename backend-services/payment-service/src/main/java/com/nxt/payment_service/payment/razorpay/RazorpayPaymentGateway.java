package com.nxt.payment_service.payment.razorpay;

import com.nxt.payment_service.payment.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class RazorpayPaymentGateway implements PaymentGateway {

    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNumber, String name) {
        return "";
    }
}
