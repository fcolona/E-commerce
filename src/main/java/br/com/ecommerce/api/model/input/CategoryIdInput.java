package br.com.ecommerce.api.model.input;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryIdInput {
    
    private long id;
}
