package br.com.ecommerce.api.model.response;

import java.util.List;

import br.com.ecommerce.domain.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    
    private Long id;
    private String email;
    private List<Role> roles;
}
