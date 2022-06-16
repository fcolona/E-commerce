package br.com.ecommerce.api.controller;

import br.com.ecommerce.api.assembler.CartAssembler;
import br.com.ecommerce.api.model.input.CartInput;
import br.com.ecommerce.api.model.response.CartResponse;
import br.com.ecommerce.api.model.response.CartWithItemsResponse;
import br.com.ecommerce.domain.model.Cart;
import br.com.ecommerce.domain.model.CartItem;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.repository.CartItemRepository;
import br.com.ecommerce.domain.repository.CartRepository;
import br.com.ecommerce.domain.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {
    private CartRepository cartRepository;

    private CartService cartService;

    private CartAssembler cartAssembler;

    private CartItemRepository cartItemRepository;

    @GetMapping
    public CartResponse getCurrentUserCart(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cart cart = cartRepository.findById(user.getId()).orElseThrow();
        return cartAssembler.toResponse(cart);
    }

    @GetMapping("/items")
    public CartWithItemsResponse getCurrentUserCartAndRetrieveCartItems(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cart cart = cartRepository.findByIdAndRetrieveItems(user.getId()).orElseThrow();
        return cartAssembler.toAnyResponse(cart, CartWithItemsResponse.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartWithItemsResponse addProductToCart(@RequestBody CartInput cartInput){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       Cart cartSaved = cartService.addToCart(user.getId(), cartInput);
       return cartAssembler.toAnyResponse(cartSaved, CartWithItemsResponse.class);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        cartRepository.deleteCartItemsByCartId(user.getId());
        cartRepository.updateTotalToZero(user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public CartWithItemsResponse updateProductQuantity(@RequestParam long cartItemId, @RequestParam int quantity){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = cartService.updateProductQuantity(user.getId(), cartItemId, quantity);

        return cartAssembler.toAnyResponse(cart, CartWithItemsResponse.class);
    }
}
