package br.com.ecommerce.domain.service;

import br.com.ecommerce.api.exception.ErrorDetails;
import br.com.ecommerce.api.exception.ResourceNotFoundException;
import br.com.ecommerce.api.model.input.UserPaymentInput;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.model.UserAddress;
import br.com.ecommerce.domain.model.UserPayment;
import br.com.ecommerce.domain.repository.UserPaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserPaymentService {

    private UserPaymentRepository userPaymentRepository;

    public UserPayment save(long userId, UserPayment userPayment){
        User user = new User();
        user.setId(userId);
        userPayment.setUser(user);

        return userPaymentRepository.save(userPayment);
    }

    public UserPayment update(long paymentId, UserPayment payment) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPayment existingPayment = userPaymentRepository.findByIdAndUserId(paymentId, user.getId()).orElseThrow( () -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("paymentId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });

        //Check if the client have sent the propertie, if so, it is set in the existing address
        //if no, it just remains the same
        existingPayment.setPaymentType(Objects.isNull(payment.getPaymentType()) ? existingPayment.getPaymentType() : (payment.getPaymentType()));
        existingPayment.setProvider(Objects.isNull(payment.getProvider()) ? existingPayment.getProvider() : (payment.getProvider()));
        existingPayment.setExpiry(Objects.isNull(payment.getExpiry()) ? existingPayment.getExpiry() : (payment.getExpiry()));

        return userPaymentRepository.save(existingPayment);
    }
}
