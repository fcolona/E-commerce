package br.com.ecommerce.api.model.input;

import br.com.ecommerce.domain.model.OrderItem;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderInput {
    @NotNull
    @Valid
    private List<OrderItemInput> orderItems;

    @NotNull
    private long addressId;

    @NotBlank
    private String stripeId;
}
