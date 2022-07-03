package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository underTest;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void itShouldDeleteCartItemsByCartId(){
        //given
        Product product = new Product();
        product.setName("Xbox Series S Controller");
        product.setPrice(350.0);
        product.setQuantityInStock(200);
        Product productSaved = productRepository.save(product);

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(userSaved);
        Cart cartSaved = underTest.save(cart);

        Set<CartItem> cartItems = new HashSet<>();
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartSaved.getId());
        cartItem.setQuantity(2);
        cartItem.setProduct(productSaved);
        cartItems.add(cartItem);
        cartSaved.setCartItems(cartItems);
        underTest.save(cartSaved);

        //when
        underTest.deleteCartItemsByCartId(cartSaved.getId());

        //then
         assertThat(underTest.findById(cartSaved.getId())).isPresent();
    }

    @Test
    void itShouldUpdateTotalToZero(){
        //given
        Product product = new Product();
        product.setName("Xbox Series S Controller");
        product.setPrice(350.0);
        product.setQuantityInStock(200);
        Product productSaved = productRepository.save(product);

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(userSaved);
        Cart cartSaved = underTest.save(cart);

        Set<CartItem> cartItems = new HashSet<>();
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartSaved.getId());
        cartItem.setQuantity(2);
        cartItem.setProduct(productSaved);
        cartItems.add(cartItem);
        cartSaved.setCartItems(cartItems);
        underTest.save(cartSaved);

        //when
        underTest.updateTotalToZero(cartSaved.getId());

        //then
        Cart cartFound = underTest.findById(cartSaved.getId()).get();
        assertThat(cartFound.getTotal()).isEqualTo(0);
    }
}
