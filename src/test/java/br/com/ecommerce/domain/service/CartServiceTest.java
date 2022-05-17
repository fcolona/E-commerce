package br.com.ecommerce.domain.service;

import br.com.ecommerce.api.model.input.CartInput;
import br.com.ecommerce.api.model.input.CartItemInput;
import br.com.ecommerce.domain.model.Cart;
import br.com.ecommerce.domain.model.CartItem;
import br.com.ecommerce.domain.model.Product;
import br.com.ecommerce.domain.repository.CartRepository;
import br.com.ecommerce.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Autowired
    private CartService underTest;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        underTest = new CartService(cartRepository, productRepository);
    }

    @Test
    void canAddToCart(){
        //given
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        cart.setCartItems(cartItems);
        cart.setTotal(0);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Product product = new Product();
        product.setName("Xbox Series S Controller");
        product.setPrice(350.0);
        product.setQuantityInStock(200);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        //when
        CartInput cartInput = new CartInput();
        CartItemInput cartItemInput = new CartItemInput();
        List<CartItemInput> cartItemInputs = new ArrayList<>();
        cartItemInput.setProductId(1L);
        cartItemInput.setQuantity(2);
        cartItemInputs.add(cartItemInput);
        cartInput.setCartItems(cartItemInputs);
        underTest.addToCart(1L, cartInput);

        //then
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void canUpdateProductQuantity(){
        //given
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setCartId(1L);
        cartItem.setQuantity(1);
        cartItem.setCartItemId(1L);
        Product product = new Product();
        product.setName("Xbox Series S Controller");
        product.setPrice(350.0);
        product.setQuantityInStock(200);
        cartItem.setProduct(product);
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        cart.setTotal(350.0);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        //when
        underTest.updateProductQuantity(1L, 1L, 2);

        //then
        cart.getCartItems().get(0).setQuantity(2);
        cart.setTotal(700.0);
        verify(cartRepository).save(cart);
    }
}
