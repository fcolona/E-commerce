package br.com.ecommerce.api.assembler;

import br.com.ecommerce.api.model.input.CartInput;
import br.com.ecommerce.api.model.response.CartResponse;
import br.com.ecommerce.domain.model.Cart;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CartAssembler {
    private ModelMapper modelMapper;

    public Cart toEntity(CartInput cartInput){
        return modelMapper.map(cartInput, Cart.class);
    }

    public CartResponse toResponse(Cart cart){
        return modelMapper.map(cart, CartResponse.class);
    }

    public List<CartResponse> toCollectionResponse(List<Cart> carts){

        return carts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public <T> T toAnyResponse(Cart entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}
