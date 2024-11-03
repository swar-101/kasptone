package com.nxt.payment_service.dto;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class PaymentLinkRequestBuilder {
    private Long amount;
    private String currency = "INR";
    private String referenceId;
    private boolean acceptPartial = true;
    private int firstMinPartialAmount = 100;
    private long expireBy = 1722914180L; // todo: Identify this timestamp
    private String description;
    private String phoneNumber;
    private String name;
    private String callbackUrl = "https://example-callback-url.com";
    private String callbackMethod = "get";

    public PaymentLinkRequestBuilder setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public PaymentLinkRequestBuilder setReferenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public PaymentLinkRequestBuilder setCustomerDetails(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        return this;
    }
    
    public JSONObject build() {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency", currency);
        paymentLinkRequest.put("accept_partial", acceptPartial);
        paymentLinkRequest.put("first_min_partial_amount", firstMinPartialAmount);
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
        paymentLinkRequest.put("callback_url", callbackUrl);
        paymentLinkRequest.put("callback_method", callbackMethod);

        return paymentLinkRequest;
    }
}