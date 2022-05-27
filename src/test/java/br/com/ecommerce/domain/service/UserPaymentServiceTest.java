package br.com.ecommerce.domain.service;

import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.model.UserPayment;
import br.com.ecommerce.domain.repository.UserPaymentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserPaymentServiceTest {
    @Autowired
    private UserPaymentService underTest;

    @Mock
    private UserPaymentRepository userPaymentRepository;

    @BeforeEach
    void setUp(){
        underTest = new UserPaymentService(userPaymentRepository);
    }

    @AfterAll
    static void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void canSavePayment(){
        //given
        UserPayment payment = new UserPayment();
        payment.setPaymentType("credit card");
        payment.setExpiry(new Date());
        payment.setProvider("AmigosCard");

        //when
        underTest.save(1L, payment);

        //then
        verify(userPaymentRepository).save(payment);
    }

    @Test
    void canUpdatePayment(){
        //given
        UserPayment paymentToBeReturned = new UserPayment();
        paymentToBeReturned.setPaymentType("credit card");
        paymentToBeReturned.setExpiry(new Date());
        paymentToBeReturned.setProvider("AmigosCard");
        when(userPaymentRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(paymentToBeReturned));

        User user = new User();
        user.setEmail("user@gmail.com");
        user.setId(1L);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(auth);

        //when
        UserPayment payment = new UserPayment();
        payment.setExpiry(new Date());
        payment.setProvider("AnotherAmigosCard");
        underTest.update(1L, payment);

        //then
        verify(userPaymentRepository).save(payment);
    }
}
