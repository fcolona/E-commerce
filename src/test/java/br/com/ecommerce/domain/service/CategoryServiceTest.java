package br.com.ecommerce.domain.service;

import br.com.ecommerce.api.exception.ResourceAlreadyExistsException;
import br.com.ecommerce.api.exception.ResourceNotFoundException;
import br.com.ecommerce.domain.model.Category;
import br.com.ecommerce.domain.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    
    private CategoryService underTest;

    @BeforeEach
    void setUp(){
        underTest = new CategoryService(categoryRepository);
    }

    @Test
    void canSaveCategory(){
        //given
        Category category = new Category();
        category.setName("GPUs");

        //when
        underTest.save(category);

        //then
        verify(categoryRepository).save(category);
    }

    @Test
    void itShouldThrowCategoryAlreadyExists(){
        //given
        Category existingCategory = new Category();
        existingCategory.setName("GPUs");
        when(categoryRepository.findByName("GPUs")).thenReturn(Optional.of(existingCategory));

        //when
        Category category = new Category();
        category.setName("GPUs");
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            //when
            underTest.save(category);
        });
    }

    @Test
    void itShouldThrowCategoryNotFound(){
        //given
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Category category = new Category();
        category.setName("GPUs");
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            //when
            underTest.update(1L, category);
        });
    }

    @Test
    void canUpdateCategoryName(){
        //given
        Category categoryToBeReturned = new Category();
        categoryToBeReturned.setName("GPUs");
        categoryToBeReturned.setId(1);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryToBeReturned));
        Category category = new Category();
        category.setName("CPUs");

        //when
        underTest.update(1L, category);

        //then
        category.setId(1L);
        verify(categoryRepository).save(category);
    }
}
