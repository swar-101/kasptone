package com.nxt.payment_service.payment.razorpay;

import com.nxt.payment_service.exception.RazorpayPaymentCreationException;
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
    private final RazorpayPaymentLinkRequestBuilder razorpayPaymentLinkRequestBuilder;

    @Autowired
    public RazorpayPaymentGateway(RazorpayClient razorpayClient, RazorpayPaymentLinkRequestBuilder razorpayPaymentLinkRequestBuilder) {
        this.razorpayClient = razorpayClient;
        this.razorpayPaymentLinkRequestBuilder = razorpayPaymentLinkRequestBuilder;
    }

    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNumber, String name) {
        JSONObject paymentLinkRequest = razorpayPaymentLinkRequestBuilder
                .buildPaymentLinkRequest(amount, orderId, phoneNumber, name);

        try {
            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
            return paymentLink.get("short_url").toString();
        } catch (RazorpayException e) {
            throw new RazorpayPaymentCreationException("Unable to create payment link due to ", e);
        }
    }
}