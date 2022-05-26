package br.com.ecommerce.api.controller;

import br.com.ecommerce.api.assembler.UserPaymentAssembler;
import br.com.ecommerce.api.exception.ErrorDetails;
import br.com.ecommerce.api.exception.UserDoesNotHaveResourceRegistered;
import br.com.ecommerce.api.model.input.UserAddressInput;
import br.com.ecommerce.api.model.input.UserPaymentInput;
import br.com.ecommerce.domain.model.User;
import br.com.ecommerce.domain.model.UserAddress;
import br.com.ecommerce.domain.model.UserPayment;
import br.com.ecommerce.domain.repository.UserPaymentRepository;
import br.com.ecommerce.domain.service.UserPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user-payment")
@AllArgsConstructor
public class UserPaymentController {

    private UserPaymentRepository userPaymentRepository;

    private UserPaymentAssembler userPaymentAssembler;

    private UserPaymentService userPaymentService;

    @GetMapping
    public List<UserPayment> getUserPayments(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<UserPayment> payments = userPaymentRepository.findByUserId(user.getId());
        if(payments.isEmpty()){
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("payments", "User does not have a registered payment"));
            throw new UserDoesNotHaveResourceRegistered(fields);
        }

        return payments;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserPayment createUserPayment(@RequestBody UserPaymentInput userPaymentInput){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPayment userPayment = userPaymentAssembler.toEntity(userPaymentInput);

        return userPaymentService.save(user.getId(), userPayment);
    }

    @PutMapping("/{paymentId}")
    public UserPayment updateUserPayment(@PathVariable long paymentId, @RequestBody UserPaymentInput userPaymentInput){
        UserPayment userPayment = userPaymentAssembler.toEntity(userPaymentInput);

        return userPaymentService.update(paymentId, userPayment);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deleteUserPayment(@PathVariable long paymentId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userPaymentRepository.deleteByIdAndUserId(paymentId, user.getId());

        return ResponseEntity.noContent().build();
    }
}
