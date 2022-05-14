package br.com.ecommerce.domain.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.ecommerce.api.exception.ErrorDetails;
import br.com.ecommerce.api.exception.ResourceAlreadyExistsException;
import br.com.ecommerce.domain.model.Cart;
import br.com.ecommerce.domain.repository.CartRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ecommerce.domain.model.Role;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    private CartRepository cartRepository;
    
    public User save(User user) throws ResourceAlreadyExistsException{
        boolean userAlreadyExits = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userAlreadyExits ){
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("email", "Email already exists"));
            throw new ResourceAlreadyExistsException(fields);
        }

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1);

        roles.add(role);
        user.setRoles(roles); 

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));

        User userSaved = userRepository.save(user);
        Cart cart = new Cart();
        cart.setUser(userSaved);
        cartRepository.save(cart);

        return userSaved;
    }
}
