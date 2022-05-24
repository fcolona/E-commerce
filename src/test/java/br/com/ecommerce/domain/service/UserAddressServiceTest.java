package br.com.ecommerce.domain.service;


import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.model.UserAddress;
import br.com.ecommerce.domain.repository.UserAddressRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAddressServiceTest {

    @Autowired
    private UserAddressService underTest;

    @Mock
    private UserAddressRepository userAddressRepository;

    @BeforeEach
    void setUp(){
        underTest = new UserAddressService(userAddressRepository);
    }

    @AfterAll
    static void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void canSaveAddress(){
        //given
        UserAddress address = new UserAddress();
        address.setMobile("11765028831");
        address.setPostalCode("12345-1234");
        address.setCity("Sao Paulo");
        address.setCountry("Brazil");
        address.setAddressLine1("Rua Test");
        address.setAddressLine2("Bairro Test");

        //when
        underTest.save(1L, address);

        //then
        verify(userAddressRepository).save(address);
    }

    @Test
    void canUpdateAddress(){
        //given
        UserAddress addressToBeReturned = new UserAddress();
        addressToBeReturned.setMobile("11765028831");
        addressToBeReturned.setPostalCode("12345-1234");
        addressToBeReturned.setCity("Sao Paulo");
        addressToBeReturned.setCountry("Brazil");
        addressToBeReturned.setAddressLine1("Rua Test");
        addressToBeReturned.setAddressLine2("Bairro Test");
        when(userAddressRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(addressToBeReturned));

        User user = new User();
        user.setEmail("user@gmail.com");
        user.setId(1L);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(auth);

        //when
        UserAddress address = new UserAddress();
        address.setCountry("USA");
        address.setCity("New York");
        underTest.update(1L, address);

        //then
        verify(userAddressRepository).save(address);
    }
}
