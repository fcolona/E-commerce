package br.com.ecommerce.api.controller;

import br.com.ecommerce.api.assembler.OrderAssembler;
import br.com.ecommerce.api.model.input.OrderInput;
import br.com.ecommerce.domain.model.Order;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.repository.OrderRepository;
import br.com.ecommerce.domain.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {
    private OrderRepository orderRepository;
    private OrderService orderService;

    private OrderAssembler orderAssembler;

    @GetMapping
    public List<Order> getCurrentUserOrders(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return orderRepository.findByUserId(user.getId());
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable long orderId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return orderRepository.findByIdAndUserId(orderId, user.getId()).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody OrderInput orderInput){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return orderService.save(orderInput, user.getId());
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> refund(@PathVariable long orderId) throws StripeException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId()).orElseThrow();
        RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(order.getStripeId()).build();
        Refund.create(params);
        orderRepository.delete(order);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}")
    public Order updateOrderStatus(@PathVariable long orderId, @RequestBody Order.StatusEnum status){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId()).orElseThrow();
        orderService.updateStatus(user, order, status);

        return order;
    }
}
