package com.nxt.payment_service.controller;

import com.nxt.payment_service.dto.PaymentDTO;
import com.nxt.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public String initializePayment(@RequestBody PaymentDTO paymentDTO){
        return paymentService.getPaymentLink(paymentDTO);
    }
}