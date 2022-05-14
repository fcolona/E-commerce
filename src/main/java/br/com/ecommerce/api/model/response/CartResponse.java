package br.com.ecommerce.api.model.response;

import br.com.ecommerce.domain.model.CartItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartResponse {

    @EqualsAndHashCode.Include
    private long id;

    private List<CartItem> cartItems;

    private double total;
}
