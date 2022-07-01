package br.com.ecommerce.api.controller;

import br.com.ecommerce.api.assembler.CartAssembler;
import br.com.ecommerce.api.common.ApiRoleAccessNotes;
import br.com.ecommerce.api.model.input.CartInput;
import br.com.ecommerce.api.model.response.CartResponse;
import br.com.ecommerce.api.model.response.CartWithItemsResponse;
import br.com.ecommerce.domain.model.Cart;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.repository.CartItemRepository;
import br.com.ecommerce.domain.repository.CartRepository;
import br.com.ecommerce.domain.service.CartService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {
    private CartRepository cartRepository;

    private CartService cartService;

    private CartAssembler cartAssembler;

    private CartItemRepository cartItemRepository;

    @GetMapping
    @ApiOperation(value = "Return current user cart")
    @ApiRoleAccessNotes("ROLE_USER")
    public CartResponse getCurrentUserCart(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cart cart = cartRepository.findById(user.getId()).orElseThrow();
        return cartAssembler.toResponse(cart);
    }

    @GetMapping("/items")
    @Cacheable(value = "cart", key = "#authentication.getPrincipal().getId()")
    @ApiOperation(value = "Return current user cart and retrieve its items")
    @ApiRoleAccessNotes("ROLE_USER")
    public CartWithItemsResponse getCurrentUserCartAndRetrieveCartItems(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        Cart cart = cartRepository.findByIdAndRetrieveItems(user.getId()).orElseThrow();
        return cartAssembler.toAnyResponse(cart, CartWithItemsResponse.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CachePut(value = "cart", key = "#authentication.getPrincipal().getId()")
    @ApiOperation(value = "Add a product to current user cart")
    @ApiRoleAccessNotes("ROLE_USER")
    public CartWithItemsResponse addProductToCart(@RequestBody CartInput cartInput, Authentication authentication){
        User user = (User) authentication.getPrincipal();

       Cart cartSaved = cartService.addToCart(user.getId(), cartInput);
       return cartAssembler.toAnyResponse(cartSaved, CartWithItemsResponse.class);
    }

    @DeleteMapping
    @CacheEvict(value = "cart", key = "#authentication.getPrincipal().getId()")
    @ApiOperation(value = "Clear current user cart")
    @ApiRoleAccessNotes("ROLE_USER")
    public ResponseEntity<Void> clearCart(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        cartRepository.deleteCartItemsByCartId(user.getId());
        cartRepository.updateTotalToZero(user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @CachePut(value = "cart", key = "#authentication.getPrincipal().getId()")
    @ApiOperation(value = "Update the quantity of a product inside the current user cart")
    @ApiRoleAccessNotes("ROLE_USER")
    public CartWithItemsResponse updateProductQuantity(@RequestParam long cartItemId, @RequestParam int quantity, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Cart cart = cartService.updateProductQuantity(user.getId(), cartItemId, quantity);

        return cartAssembler.toAnyResponse(cart, CartWithItemsResponse.class);
    }
}
