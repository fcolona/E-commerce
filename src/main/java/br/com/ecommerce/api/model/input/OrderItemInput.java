package br.com.ecommerce.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemInput {
    @NotNull
    private long productId;

    @NotNull
    private int quantity;
}
