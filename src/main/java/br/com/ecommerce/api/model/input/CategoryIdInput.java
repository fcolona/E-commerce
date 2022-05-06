package br.com.ecommerce.api.model.input;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryIdInput {

    @NotNull
    private long id;
}
