package br.com.ecommerce.api.model.response;

import br.com.ecommerce.domain.model.Order;
import br.com.ecommerce.domain.model.Role;
import br.com.ecommerce.domain.model.UserAddress;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWithRolesAndAddressesAndOrdersResponse {
    private Long id;
    private String email;
    private List<Role> roles;
    private Set<UserAddress> addresses;
    private Set<Order> orders;
}
