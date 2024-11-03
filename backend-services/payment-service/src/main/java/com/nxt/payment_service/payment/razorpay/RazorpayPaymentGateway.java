package com.nxt.payment_service.payment.razorpay;

import com.nxt.payment_service.dto.PaymentLinkRequestBuilder;
import com.nxt.payment_service.exception.PaymentProcessingException;
import com.nxt.payment_service.payment.PaymentGateway;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RazorpayPaymentGateway implements PaymentGateway {

    private final RazorpayClient razorpayClient;
    private final PaymentLinkRequestBuilder paymentLinkRequestBuilder;

    @Autowired
    public RazorpayPaymentGateway(RazorpayClient razorpayClient, PaymentLinkRequestBuilder paymentLinkRequestBuilder) {
        this.razorpayClient = razorpayClient;
        this.paymentLinkRequestBuilder = paymentLinkRequestBuilder;
    }

    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNumber, String name) {
        try {
            JSONObject paymentLinkRequest = paymentLinkRequestBuilder
                    .setAmount(amount)
                    .setCustomerDetails(phoneNumber, name)
                    .setReferenceId(orderId).build();
            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
            return paymentLink.get("short_url").toString();
        } catch (RazorpayException e) {
            throw new PaymentProcessingException("Unable to create payment link due to ", e);
        }
    }
}