package br.com.ecommerce.api.model.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartInput {
    private List<CartItemInput> cartItems;
}
