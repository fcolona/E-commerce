package br.com.ecommerce.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ProductInput {
    @Size(max = 255)
    private String name;

    private Integer quantity;

    private Double price;

    private List<CategoryIdInput> categories;
}
