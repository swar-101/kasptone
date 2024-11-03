package com.nxt.payment_service.payment;

import com.nxt.payment_service.payment.razorpay.RazorpayPaymentGateway;
import com.nxt.payment_service.payment.stripe.StripePaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code PaymentGatewayStrategy} class provides a mechanism for selecting
 * the appropriate payment gateway based on a calculated decision factor.
 *
 * <p>This decision factor determines which payment gateway—either
 * {@link RazorpayPaymentGateway} or {@link StripePaymentGateway}—should be used
 * to initiate the payment. The selection is designed to balance the load and ensure
 * optimal gateway utilization.</p>
 *
 * <p>Usage example:</p>
 * <pre>
 *     PaymentGatewayStrategy gatewayStrategy = new PaymentGatewayStrategy(razorpay, stripe);
 *     PaymentGateway optimalGateway = gatewayStrategy.getOptimalPaymentGateway();
 * </pre>
 *
 * <p>This class uses Spring's dependency injection to inject the instances of
 * available payment gateways.</p>
 *
 * @see RazorpayPaymentGateway
 * @see StripePaymentGateway
 */
@Component
public class PaymentGatewayStrategy {

    private final RazorpayPaymentGateway razorpayPaymentGateway;
    private final StripePaymentGateway stripePaymentGateway;

    @Autowired
    public PaymentGatewayStrategy(RazorpayPaymentGateway razorpayPaymentGateway, StripePaymentGateway stripePaymentGateway) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }

    /**
     * Determines the optimal payment gateway based on a randomly generated decision factor.
     *
     * <p>This method generates an integer value, {@code gatewayDecisionFactor},
     * in the range of 0 to 9. If the value is greater than 4, the
     * {@link RazorpayPaymentGateway} is selected; otherwise, the
     * {@link StripePaymentGateway} is chosen.</p>
     *
     * <p>The approach provides a probabilistic selection, slightly favoring
     * {@link RazorpayPaymentGateway} over {@link StripePaymentGateway}.</p>
     *
     * @return the selected {@link PaymentGateway} instance, either Razorpay or Stripe
     */
    public PaymentGateway getOptimalPaymentGateway() {
        if (getGatewayDecisionFactor() > 4) {
            return razorpayPaymentGateway;
        } else {
            return stripePaymentGateway;
        }
    }

    private int getGatewayDecisionFactor() {
        return ThreadLocalRandom.current().nextInt(0, 10);
    }
}