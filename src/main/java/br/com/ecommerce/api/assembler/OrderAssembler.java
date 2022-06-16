package br.com.ecommerce.api.assembler;

import br.com.ecommerce.api.model.input.OrderInput;
import br.com.ecommerce.domain.model.Order;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderAssembler {
    private ModelMapper modelMapper;

    public Order toEntity(OrderInput orderInput){
        return modelMapper.map(orderInput, Order.class);
    }

    public <T> T toAnyResponse(Order entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}
