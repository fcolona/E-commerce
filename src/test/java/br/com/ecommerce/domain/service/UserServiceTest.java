package br.com.ecommerce.domain.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.ecommerce.api.exception.ResourceAlreadyExistsException;
import br.com.ecommerce.domain.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.repository.UserRepository;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    private UserService underTest;

    @BeforeEach
    void setUp(){
        underTest = new UserService(userRepository, cartRepository);
    }

    @Test
    void canSaveUser(){
        //given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");

        //when
        underTest.save(user);

        //then
        verify(userRepository).save(user); 
    }

    @Test()
     void itShouldThrowEmailAlreadyExists(){
        //given
        User existingUser = new User();
        existingUser.setEmail("user@gmail.com");
        existingUser.setPassword("123");
        when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(existingUser));


        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");
        //then
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            //when
            underTest.save(user);
        });
    }
}
