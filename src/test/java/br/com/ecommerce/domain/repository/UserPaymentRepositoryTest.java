package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.Role;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.model.UserPayment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserPaymentRepositoryTest {
    @Autowired
    private UserPaymentRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldFindPaymentByUserId(){
        //given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        UserPayment payment = new UserPayment();
        payment.setPaymentType("credit card");
        payment.setExpiry(new Date());
        payment.setProvider("AmigosCard");
        payment.setUser(userSaved);

        underTest.save(payment);

        //when
        List<UserPayment> paymentsFound = underTest.findByUserId(userSaved.getId());

        //then
        assertThat(paymentsFound.get(0)).isEqualTo(payment);
    }

    @Test
    void itShouldFindPaymentByIdAndUserId(){
        //given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        UserPayment payment = new UserPayment();
        payment.setPaymentType("credit card");
        payment.setExpiry(new Date());
        payment.setProvider("AmigosCard");
        payment.setUser(userSaved);

        UserPayment paymentSaved = underTest.save(payment);

        //when
        Optional<UserPayment> paymentFound = underTest.findByIdAndUserId(paymentSaved.getId(), userSaved.getId());

        //then
        assertThat(paymentFound).isPresent();
    }

    @Test
    void itShouldNotFindPaymentByIdAndUserId(){
        //given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        UserPayment payment = new UserPayment();
        payment.setPaymentType("credit card");
        payment.setExpiry(new Date());
        payment.setProvider("AmigosCard");
        payment.setUser(userSaved);

        //when
        Optional<UserPayment> paymentFound = underTest.findByIdAndUserId(36L, userSaved.getId());

        //then
        assertThat(paymentFound).isEmpty();
    }

    @Test
    void itShouldDeletePaymentByIdAndUserId(){
        //given
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("123");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        User userSaved = userRepository.save(user);

        UserPayment payment = new UserPayment();
        payment.setPaymentType("credit card");
        payment.setExpiry(new Date());
        payment.setProvider("AmigosCard");
        payment.setUser(userSaved);
        UserPayment paymentSaved = underTest.save(payment);

        //when
        underTest.deleteByIdAndUserId(paymentSaved.getId(), user.getId());

        //then
        assertThat(underTest.findById(payment.getId())).isEmpty();
    }
}

