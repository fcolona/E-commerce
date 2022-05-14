package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.Category;
import br.com.ecommerce.domain.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository underTest;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldDeleteFromLinkTable(){
        //given
        List<Category> categories = new ArrayList<>();
        Category gamingCategory = new Category();
        gamingCategory.setName("Gaming");
        Category technologyCategory = new Category();
        technologyCategory.setName("Technology");
        categories.add(gamingCategory);
        categories.add(technologyCategory);
        categoryRepository.saveAll(categories);

        Product product = new Product();
        product.setName("Xbox Series S Controller");
        product.setPrice(350.0);
        product.setQuantityInStock(200);
        product.setCategories(categories);
        Product productSaved = underTest.save(product);

        //when
        underTest.deleteFromLinkTable(productSaved.getId());
        underTest.deleteById(productSaved.getId());

        //then
        assertThat(underTest.findById(productSaved.getId())).isEmpty();
    }
}
