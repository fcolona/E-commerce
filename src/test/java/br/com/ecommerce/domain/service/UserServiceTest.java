package br.com.ecommerce.domain.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.repository.UserRepository;

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
}
