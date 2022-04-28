package br.com.ecommerce.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.ecommerce.api.model.input.ProductInput;
import br.com.ecommerce.domain.model.Product;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductAssembler {
    private final ModelMapper modelMapper;

    public Product toEntity(ProductInput productInput) {
        return modelMapper.map(productInput, Product.class);
    }
}
