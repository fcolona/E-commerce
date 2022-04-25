package br.com.ecommerce.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.ecommerce.domain.model.Role;
import br.com.ecommerce.domain.model.User;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    
    @Autowired
    private UserRepository underTest;

    @Autowired
    private RoleRepository roleRepository;
    
    @BeforeAll
    void setUp(){
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);

        Role adminRole = new Role();
        userRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);
    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail(){
        //given
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findById(1L).get();
        roles.add(role);
        User user = new User("user@gmail.com", "123");
        user.setRoles(roles);
        underTest.save(user);

        //when
        Optional<User> userOptional = underTest.findByEmail(user.getEmail());

        //then
        assertThat(userOptional).isPresent();
    }

    @Test
    void itShouldNotFindUserByEmail(){
        //when
        Optional<User> userOptional = underTest.findByEmail("user@gmail.com");

        //then
        assertThat(userOptional).isEmpty();
    }

    @Test
    void itShouldDeleteFromLinkTable(){
        //given
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findById(1L).get();
        roles.add(role);
        User user = new User("user@gmail.com", "123");
        user.setRoles(roles);
        User userSaved = underTest.save(user);       

        //when
        underTest.deleteFromLinkTable(userSaved.getId());
        underTest.deleteById(userSaved.getId());

        //then
        assertThat(underTest.findById(userSaved.getId())).isEmpty();
    }
}
