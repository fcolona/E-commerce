package br.com.ecommerce.domain.service;

import br.com.ecommerce.api.exception.ErrorDetails;
import br.com.ecommerce.api.exception.ResourceNotFoundException;
import br.com.ecommerce.api.model.input.CartInput;
import br.com.ecommerce.domain.model.Cart;
import br.com.ecommerce.domain.model.CartItem;
import br.com.ecommerce.domain.model.Product;
import br.com.ecommerce.domain.repository.CartRepository;
import br.com.ecommerce.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;

    private ProductRepository productRepository;

    @Transactional
    public Cart addToCart(long cartId, CartInput cartInput){
       Cart existingCart = cartRepository.findByIdAndRetrieveItems(cartId).orElseThrow(() -> {
           Set<ErrorDetails.Field> fields = new HashSet<>();
           fields.add(new ErrorDetails.Field("cartId", "Id given do not match"));
           throw new ResourceNotFoundException(fields);
       });

        cartInput.getCartItems().forEach( (item -> {
            CartItem cartItem = new CartItem();
            cartItem.setCartId(existingCart.getId());
            cartItem.setQuantity(item.getQuantity());

            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> {
                Set<ErrorDetails.Field> fields = new HashSet<>();
                fields.add(new ErrorDetails.Field("productId", "Id given do not match"));
                throw new ResourceNotFoundException(fields);
            });
            cartItem.setProduct(product);

            existingCart.getCartItems().add(cartItem);
            existingCart.setTotal(existingCart.getTotal() + (cartItem.getQuantity() * product.getPrice()));
        }));

       return cartRepository.save(existingCart);
    }

    public Cart updateProductQuantity(long cartId, long cartItemId, int quantity) {
        Cart cart = cartRepository.findByIdAndRetrieveItems(cartId).orElseThrow();
        try{
            //Filter the items looking for the cart with given id
            CartItem cartItem = cart.getCartItems().stream().filter((item) -> item.getCartItemId() == cartItemId).toList().get(0);

            //Checks if the quantity of the product is equal to zero
            if(quantity == 0){
               //If so, subtracts the product price from the total
               cart.setTotal(cart.getTotal() - cartItem.getProduct().getPrice());

               //And removes the item from the cart
               cart.getCartItems().remove(cartItem);

               return cartRepository.save(cart);
            }

            //Stores quantity before updating
            int pastQuantity = cartItem.getQuantity();

            //Subtract past price from total
            cart.setTotal(cart.getTotal() - (cartItem.getProduct().getPrice() * pastQuantity));

            //Update quantity
            cartItem.setQuantity(quantity);

            //Add the new price
            cart.setTotal(cart.getTotal() + (cartItem.getProduct().getPrice() * cartItem.getQuantity()));

            return cartRepository.save(cart);
        }catch (IndexOutOfBoundsException err){
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("productId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        }
    }
}
