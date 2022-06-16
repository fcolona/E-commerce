package br.com.ecommerce.api.model.response;

import br.com.ecommerce.domain.model.CartItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {

    @EqualsAndHashCode.Include
    private long id;
    private double total;
}
