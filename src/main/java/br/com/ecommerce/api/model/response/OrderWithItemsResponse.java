package br.com.ecommerce.api.model.response;

import br.com.ecommerce.domain.model.Order;
import br.com.ecommerce.domain.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderWithItemsResponse {
    private long id;
    private Set<OrderItem> orderItems;
    private long userId;
    private String stripeId;
    private Order.StatusEnum status;
}
