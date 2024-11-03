package com.nxt.payment_service.payment.razorpay;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class RazorpayPaymentLinkRequestBuilder {
    public static final String INR = "INR";
    private Long amount;
    private String referenceId;
    private String description;
    private String phoneNumber;
    private String name;

    public RazorpayPaymentLinkRequestBuilder setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public RazorpayPaymentLinkRequestBuilder setReferenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public RazorpayPaymentLinkRequestBuilder setCustomerDetails(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        return this;
    }

    public JSONObject build() {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency", INR);
        boolean acceptPartial = true;
        paymentLinkRequest.put("accept_partial", acceptPartial);
        int firstMinPartialAmount = 100;
        paymentLinkRequest.put("first_min_partial_amount", firstMinPartialAmount);
        // todo: Identify this timestamp
        long expireBy = 1722914180L;
        paymentLinkRequest.put("expire_by", expireBy);
        paymentLinkRequest.put("reference_id", referenceId);
        paymentLinkRequest.put("description", description);

        JSONObject customer = new JSONObject();
        customer.put("name", name);
        customer.put("contact", phoneNumber);
        customer.put("email", "N/A");
        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        customer.put("sms", true);
        customer.put("email", true);
        paymentLinkRequest.put("notify", notify);

        paymentLinkRequest.put("remind_enable", true);

        JSONObject notes = new JSONObject();
        notes.put("policy_name", "N/A");
        paymentLinkRequest.put("notes", notes);
        String callbackUrl = "https://example-callback-url.com";
        paymentLinkRequest.put("callback_url", callbackUrl);
        String callbackMethod = "get";
        paymentLinkRequest.put("callback_method", callbackMethod);

        return paymentLinkRequest;
    }

    public JSONObject buildPaymentLinkRequest(Long amount, String orderId, String phoneNumber, String name) {
        return this.setAmount(amount).setCustomerDetails(phoneNumber, name).setReferenceId(orderId).build();
    }
}