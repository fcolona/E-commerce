package br.com.ecommerce.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import br.com.ecommerce.domain.model.Category;

@DataJpaTest
public class CategoryRepositoryTest {
    
    @Autowired
    private CategoryRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
   void itShouldFindByCategoryName(){
       //given
       Category category = new Category();
       category.setName("GPUs");
       underTest.save(category);

       //when
       Optional<Category> categoryFound = underTest.findByName("GPUs");

       //then
       assertThat(categoryFound).isPresent();
   }

   @Test
   void itShouldNotFindByCategoryName(){
       //when
       Optional<Category> categoryFound = underTest.findByName("GPUs");

       //then
       assertThat(categoryFound).isEmpty();
   }
}
