package br.com.ecommerce.domain.repository;


import br.com.ecommerce.domain.model.Role;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.model.UserAddress;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserAddressRepositoryTest {

    @Autowired
    private UserAddressRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldFindAddressByUserId(){
        //given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        UserAddress address = new UserAddress();
        address.setMobile("11765028831");
        address.setPostalCode("12345-1234");
        address.setCity("Sao Paulo");
        address.setCountry("Brazil");
        address.setAddressLine1("Rua Test");
        address.setAddressLine2("Bairro Test");
        address.setUser(userSaved);
        underTest.save(address);

        //when
        List<UserAddress> addressFound = underTest.findByUserId(userSaved.getId());

        //then
        assertThat(addressFound.get(0)).isEqualTo(address);
    }

    @Test
    void itShouldFindAddressByIdAndUserId(){
        //given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        UserAddress address = new UserAddress();
        address.setMobile("11765028831");
        address.setPostalCode("12345-1234");
        address.setCity("Sao Paulo");
        address.setCountry("Brazil");
        address.setAddressLine1("Rua Test");
        address.setAddressLine2("Bairro Test");
        address.setUser(userSaved);
        UserAddress addressSaved = underTest.save(address);

        //when
        Optional<UserAddress> addressFound = underTest.findByIdAndUserId(addressSaved.getId(), userSaved.getId());

        //then
        assertThat(addressFound).isPresent();
    }

    @Test
    void itShouldNotFindAddressByIdAndUserId(){
        //given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        UserAddress address = new UserAddress();
        address.setMobile("11765028831");
        address.setPostalCode("12345-1234");
        address.setCity("Sao Paulo");
        address.setCountry("Brazil");
        address.setAddressLine1("Rua Test");
        address.setAddressLine2("Bairro Test");
        address.setUser(userSaved);
        UserAddress addressSaved = underTest.save(address);

        //when
        Optional<UserAddress> addressFound = underTest.findByIdAndUserId(36L, userSaved.getId());

        //then
        assertThat(addressFound).isEmpty();
    }

    @Test
    void itShouldDeleteAddressByIdAndUserId(){
        //given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        UserAddress address = new UserAddress();
        address.setMobile("11765028831");
        address.setPostalCode("12345-1234");
        address.setCity("Sao Paulo");
        address.setCountry("Brazil");
        address.setAddressLine1("Rua Test");
        address.setAddressLine2("Bairro Test");
        address.setUser(userSaved);
        UserAddress addressSaved = underTest.save(address);

        //when
        underTest.deleteByIdAndUserId(addressSaved.getId(), user.getId());

        //then
        assertThat(underTest.findById(address.getId())).isEmpty();
    }
}
