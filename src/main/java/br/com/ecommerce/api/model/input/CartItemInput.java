package br.com.ecommerce.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemInput {

    private long productId;
    private int quantity;
}
