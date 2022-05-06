package br.com.ecommerce.domain.service;


import br.com.ecommerce.api.exception.ResourceNotFoundException;
import br.com.ecommerce.domain.model.Category;
import br.com.ecommerce.domain.model.Product;
import br.com.ecommerce.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private ProductService underTest;

    @BeforeEach
    void setUp(){
        underTest = new ProductService(productRepository, jdbcTemplate);
    }

    @Test
    void canSaveProduct(){
        //given
        Product product = new Product();
        product.setName("Xbox Series S Controller");
        product.setPrice(350.0);
        product.setQuantity(200);

        //when
        underTest.save(product);

        //then
        verify(productRepository).save(product);
    }

    @Test
    void itShouldGetProductsByCategories() throws SQLException {
        //given
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return Sort.by("name").descending();
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("gaming");
        categoryNames.add("technology");

        //when
        underTest.getProductsByCategories(pageable, categoryNames);

        //then
        verify(jdbcTemplate).queryForList("SELECT product.product_id, product.name, product.quantity FROM product JOIN products_categories ON product.product_id = products_categories.product_id JOIN category ON category.category_id = products_categories.category_id WHERE category.name = 'gaming' OR category.name = 'technology' GROUP BY product.product_id ORDER BY name DESC LIMIT 10 OFFSET 0");
    }

    @Test
    void canUpdateProduct(){
        //given
        List<Category> categories = new ArrayList<>();
        Category gamingCategory = new Category();
        gamingCategory.setName("Gaming");
        Category technologyCategory = new Category();
        technologyCategory.setName("Technology");
        categories.add(gamingCategory);
        categories.add(technologyCategory);

        Product productToBeReturned = new Product();
        productToBeReturned.setName("Xbox Series S Controller");
        productToBeReturned.setPrice(350.0);
        productToBeReturned.setQuantity(200);
        productToBeReturned.setCategories(categories);
        when(productRepository.findById(1L)).thenReturn(Optional.of(productToBeReturned));
        Product product = new Product();
        product.setName("Xbox Series S/X");
        product.setPrice(400.);
        product.setQuantity(190);
        categories.remove(1);
        product.setCategories(categories);

        //when
        underTest.update(1L, product);

        //then
        verify(productRepository).save(product);
    }

    @Test
    void itShouldThrowProductNotFound(){
        //given
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product product = new Product();
        product.setName("Xbox Series S/X");
        product.setPrice(400.);
        product.setQuantity(190);
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            //when
            underTest.update(1L, product);
        });
    }
}
