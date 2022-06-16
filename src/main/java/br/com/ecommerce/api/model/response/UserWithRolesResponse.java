package br.com.ecommerce.api.model.response;

import br.com.ecommerce.domain.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWithRolesResponse {
    private Long id;
    private String email;
    private List<Role> roles;
}
