package com.nxt.payment_service.payment.stripe;

import com.nxt.payment_service.exception.StripePaymentCreationError;
import com.nxt.payment_service.exception.StripePriceCreationError;
import com.nxt.payment_service.payment.PaymentGateway;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements PaymentGateway {

    @Value("${nxt.stripe.key}")
    private String apiKey;

    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNumber, String name) {
        Stripe.apiKey = this.apiKey;
        Price price = getPrice(amount);

        PaymentLinkCreateParams paymentLinkParams = PaymentLinkCreateParams.builder()
                .addLineItem(getLineItems(price))
                .setAfterCompletion(getAfterCompletion(price)).build();

        try {
            PaymentLink paymentLink = PaymentLink.create(paymentLinkParams);
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new StripePaymentCreationError("Unable to create payment like due to ", e);
        }
    }

    private PaymentLinkCreateParams.AfterCompletion getAfterCompletion(Price price) {
        return PaymentLinkCreateParams.AfterCompletion.builder()
                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                .setRedirect(getRedirect()).build();
    }

    private static PaymentLinkCreateParams.AfterCompletion.Redirect getRedirect() {
        return PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                .setUrl("https://www.nxt.com/orders")
                .build();
    }

    private PaymentLinkCreateParams.LineItem getLineItems(Price price) {
        return PaymentLinkCreateParams.LineItem.builder().setPrice(price.getId()).setQuantity(1L).build();
    }

    private Price getPrice(Long amount) {
        try {
            PriceCreateParams params = PriceCreateParams.builder()
                    .setCurrency("usd")
                    .setUnitAmount(amount)
                    .setRecurring(
                            getMonthlyRecurring()
                    )
                    .setProductData(
                            getGoldPlan()  //todo: What does this signify?
                    ).build();

            return Price.create(params);
        } catch (StripeException e) {
            throw new StripePriceCreationError("Error while creating Price for this Stripe Transaction", e);
        }
    }

    private static PriceCreateParams.ProductData getGoldPlan() {
        return PriceCreateParams.ProductData.builder().setName("Gold Plan").build();
    }

    private PriceCreateParams.Recurring getMonthlyRecurring() {
        return PriceCreateParams.Recurring.builder()
                .setInterval(PriceCreateParams.Recurring.Interval.MONTH).build();
    }
}