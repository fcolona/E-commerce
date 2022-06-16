package br.com.ecommerce.api.controller;

import br.com.ecommerce.api.assembler.OrderAssembler;
import br.com.ecommerce.api.exception.ErrorDetails;
import br.com.ecommerce.api.exception.ResourceNotFoundException;
import br.com.ecommerce.api.model.input.OrderInput;
import br.com.ecommerce.api.model.response.OrderResponse;
import br.com.ecommerce.api.model.response.OrderWithAddressResponse;
import br.com.ecommerce.api.model.response.OrderWithItemsAndAddressResponse;
import br.com.ecommerce.api.model.response.OrderWithItemsResponse;
import br.com.ecommerce.domain.model.Order;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.repository.OrderRepository;
import br.com.ecommerce.domain.repository.UserRepository;
import br.com.ecommerce.domain.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {
    private OrderRepository orderRepository;
    private OrderService orderService;

    private OrderAssembler orderAssembler;
    private UserRepository userRepository;

    @GetMapping
    public List<Order> getCurrentUserOrders(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return orderRepository.findByUserId(user.getId());
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable long orderId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId()).orElseThrow(() -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("orderId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });
        return orderAssembler.toAnyResponse(order, OrderResponse.class);
    }

    @GetMapping("/{orderId}/items")
    public OrderWithItemsResponse getOrderAndRetrieveItems(@PathVariable long orderId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findByIdAndUserIdAndRetrieveItems(orderId, user.getId()).orElseThrow(() -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("orderId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });
        return orderAssembler.toAnyResponse(order, OrderWithItemsResponse.class);
    }

    @GetMapping("/{orderId}/address")
    public OrderWithAddressResponse getOrderAndRetrieveAddress(@PathVariable long orderId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findByIdAndUserIdAndRetrieveAddress(orderId, user.getId()).orElseThrow(() -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("orderId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });
        return orderAssembler.toAnyResponse(order, OrderWithAddressResponse.class);
    }

    @GetMapping("/{orderId}/items-address")
    public OrderWithItemsAndAddressResponse getOrderAndRetrieveItemsAndAddress(@PathVariable long orderId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findByIdAndUserIdAndRetrieveItemsAndAddress(orderId, user.getId()).orElseThrow(() -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("orderId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });
        return orderAssembler.toAnyResponse(order, OrderWithItemsAndAddressResponse.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderWithItemsAndAddressResponse createOrder(@RequestBody OrderInput orderInput){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order orderSaved = orderService.save(orderInput, user.getId());
        return orderAssembler.toAnyResponse(orderSaved, OrderWithItemsAndAddressResponse.class);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> refund(@PathVariable long orderId) throws StripeException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findByIdAndUserId(orderId, user.getId()).orElseThrow(() -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("orderId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });
        RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(order.getStripeId()).build();
        Refund.create(params);
        orderRepository.delete(order);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}")
    public Order updateOrderStatus(@PathVariable long orderId, @RequestBody Order.StatusEnum status){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("orderId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });
        User user = userRepository.findById(order.getUserId()).orElseThrow();
        orderService.updateStatus(user.getEmail(), order, status);

        return order;
    }
}
