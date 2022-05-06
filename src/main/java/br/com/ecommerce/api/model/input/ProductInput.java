package br.com.ecommerce.api.model.input;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductInput {
    @Size(max = 255)
    private String name;

    private Integer quantity;

    private Double price;

    private List<CategoryIdInput> categories;
}
