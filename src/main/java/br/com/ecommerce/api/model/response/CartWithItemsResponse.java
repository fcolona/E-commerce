package br.com.ecommerce.api.model.response;

import br.com.ecommerce.domain.model.CartItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartWithItemsResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 7156526077883281623L;

    private long id;
    private double total;
    private Set<CartItem> cartItems;
}
