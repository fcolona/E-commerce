package br.com.ecommerce.api.model.response;

import br.com.ecommerce.domain.model.Category;
import br.com.ecommerce.domain.model.Image;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductWithCategoriesAndImagesResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 7156526077883281623L;

    private long id;
    private String name;
    private Integer quantityInStock;
    private Double price;
    private Set<Category> categories;
    private Set<Image> images;
}
