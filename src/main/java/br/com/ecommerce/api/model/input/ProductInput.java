package br.com.ecommerce.api.model.input;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInput {
    private long id;

    private String name;

    private Integer quantity;    

    private Double price;

    private List<CategoryIdInput> categories;
}
