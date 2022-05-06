package br.com.ecommerce.domain.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.ecommerce.api.exception.ResourceAlreadyExistsException;
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

    private UserService underTest;

    @BeforeEach
    void setUp(){
        underTest = new UserService(userRepository);
    }

    @Test
    void canSaveUser(){
        //given
        User user = new User("user@gmail.com", "123");

        //when
        underTest.save(user);

        //then
        verify(userRepository).save(user); 
    }

    @Test()
     void itShouldThrowEmailAlreadyExists(){
        //given
        User existingUser = new User("user@gmail.com", "123");
        when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(existingUser));


        User user = new User("user@gmail.com", "321");
        //then
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            //when
            underTest.save(user);
        });
    }
}
