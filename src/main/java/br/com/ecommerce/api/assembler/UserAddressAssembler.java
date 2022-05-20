package br.com.ecommerce.api.assembler;

import br.com.ecommerce.api.model.input.ProductInput;
import br.com.ecommerce.api.model.input.UserAddressInput;
import br.com.ecommerce.domain.model.Product;
import br.com.ecommerce.domain.model.UserAddress;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserAddressAssembler {
    private final ModelMapper modelMapper;

    public UserAddress toEntity(UserAddressInput userAddressInput) {
        return modelMapper.map(userAddressInput, UserAddress.class);
    }
}
