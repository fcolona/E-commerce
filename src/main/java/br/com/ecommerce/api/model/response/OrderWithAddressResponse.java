package br.com.ecommerce.api.model.response;

import br.com.ecommerce.domain.model.Order;
import br.com.ecommerce.domain.model.UserAddress;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderWithAddressResponse {
    private long id;
    private UserAddress userAddress;
    private long userId;
    private String stripeId;
    private Order.StatusEnum status;
}
