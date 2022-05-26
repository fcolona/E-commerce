package br.com.ecommerce.api.assembler;

import br.com.ecommerce.api.model.input.UserAddressInput;
import br.com.ecommerce.api.model.input.UserPaymentInput;
import br.com.ecommerce.domain.model.UserAddress;
import br.com.ecommerce.domain.model.UserPayment;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserPaymentAssembler {

    private final ModelMapper modelMapper;

    public UserPayment toEntity(UserPaymentInput userPaymentInput) {
        return modelMapper.map(userPaymentInput, UserPayment.class);
    }
}
