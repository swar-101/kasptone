package com.nxt.payment_service.service;

import com.nxt.payment_service.dto.PaymentDTO;
import com.nxt.payment_service.payment.PaymentGateway;
import com.nxt.payment_service.payment.PaymentGatewayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentGatewayStrategy paymentGatewayStrategy;

    @Autowired
    public PaymentService(PaymentGatewayStrategy paymentGatewayStrategy) {
        this.paymentGatewayStrategy = paymentGatewayStrategy;
    }

    public String getPaymentLink(PaymentDTO paymentDTO) {
        PaymentGateway paymentGateway = paymentGatewayStrategy.getOptimalPaymentGateway();
        Long amount = paymentDTO.getAmount();
        String orderId = paymentDTO.getOrderId();
        String phoneNumber = paymentDTO.getPhoneNumber();
        String name = paymentDTO.getName();

        return paymentGateway.getPaymentLink(amount, orderId, phoneNumber, name);
    }
}