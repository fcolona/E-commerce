package br.com.ecommerce.domain.service;

import br.com.ecommerce.api.model.input.OrderInput;
import br.com.ecommerce.domain.model.*;
import br.com.ecommerce.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${mail.smtp.username}")
    private String username;

    public Order save(OrderInput orderInput, long userId){
        Order order = new Order();
        order.setStripeId(orderInput.getStripeId());
        order.setUserId(userId);
        order.setStatus(Order.StatusEnum.IN_STORAGE);

        UserAddress userAddress = new UserAddress();
        userAddress.setId(orderInput.getAddressId());
        order.setUserAddress(userAddress);

        List<OrderItem> orderItems = new ArrayList<>();
        orderInput.getOrderItems().forEach( item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.getQuantity());

            Product product = new Product();
            product.setId(item.getProductId());
            orderItem.setProduct(product);

            orderItem.setOrder(order);
            orderItems.add(orderItem);
        });
        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    public void updateStatus(User user, Order order, Order.StatusEnum statusEnum) {
        order.setStatus(statusEnum);
        orderRepository.save(order);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(user.getEmail());
        message.setSubject("Your Product Status Changed");
        message.setText("Your product " + statusEnum.toString());

        emailSender.send(message);
    }
}
