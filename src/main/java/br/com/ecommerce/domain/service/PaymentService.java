package br.com.ecommerce.domain.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnExpression(value = "!'${STRIPE_SECRET_API_KEY}'.isBlank()")
public class PaymentService {

    @Value("${STRIPE_SECRET_API_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent save(double amount) throws StripeException {
        List<Object> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        int amountInCents = (int) (amount * 100);
        params.put("amount", amountInCents);
        params.put("currency", "brl");
        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);
    }
}
