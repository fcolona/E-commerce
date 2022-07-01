package br.com.ecommerce.api.controller;

import br.com.ecommerce.api.common.ApiRoleAccessNotes;
import br.com.ecommerce.domain.model.Cart;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.repository.CartRepository;
import br.com.ecommerce.domain.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Value("${STRIPE_PUBLIC_API_KEY}")
    private String publicKey;
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CartRepository cartRepository;

    @Controller
    public class PageRenderer {

        @GetMapping("/payment")
        @ApiOperation(value = "Page used for starting a payment")
        @ApiRoleAccessNotes("ROLE_USER")
        public String paymentDataForm(Model model){
            model.addAttribute("stripeApiKey", publicKey);
            return "payment-data-form";
        }

        @GetMapping("/payment/success")
        @ApiOperation(value = "Page where the user is redirected when the purchase is successful")
        @ApiRoleAccessNotes("ROLE_USER")
        public String paymentSuccess(){
            return "payment-success";
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a payment intent")
    @ApiRoleAccessNotes("ROLE_USER")
    public String createPaymentIntent() throws StripeException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = cartRepository.findById(user.getId()).orElseThrow();

        PaymentIntent paymentIntent = paymentService.save(cart.getTotal());
        return paymentIntent.getId();
    }

    @GetMapping("/secret/{stripeId}")
    @ApiOperation(value = "Retrieve Stripe client secret")
    @ApiRoleAccessNotes("ROLE_USER")
    public String getClientSecret(@PathVariable String stripeId) throws StripeException {
        return PaymentIntent.retrieve(stripeId).getClientSecret();
    }
}
