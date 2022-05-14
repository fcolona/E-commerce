package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.Image;
import br.com.ecommerce.domain.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository underTest;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldDeleteAllImagesByProductId(){
        //given
        Product product = new Product();
        product.setName("Xbox Series S Controller");
        product.setPrice(350.0);
        product.setQuantityInStock(200);
        productRepository.save(product);


        Image image = new Image();
        byte[] array = new byte[5];
        image.setContent(array);
        Product product2 = new Product();
        product2.setId(1);
        image.setProduct(product2);

       //when
       underTest.deleteByProductId(1);

       //then
        assertThat(underTest.findById(1L)).isEmpty();
    }
}
