package br.com.ecommerce.api.security;

import br.com.ecommerce.domain.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class ImplementsUserDetails implements UserDetailsService{
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailAndRetrieveRoles(email).orElseThrow( () -> new UsernameNotFoundException("User Not Found"));
    }
    
}
