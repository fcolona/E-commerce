package br.com.ecommerce.domain.service;

import br.com.ecommerce.api.exception.ErrorDetails;
import br.com.ecommerce.api.exception.ResourceAlreadyExistsException;
import br.com.ecommerce.api.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ecommerce.domain.model.Category;
import br.com.ecommerce.domain.repository.CategoryRepository;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    
    public Category save(Category category) throws ResourceAlreadyExistsException{
        boolean categoryAlreadyExits = categoryRepository.findByName(category.getName()).isPresent();
        if(categoryAlreadyExits){
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("name", "Category name already exists"));
            throw new ResourceAlreadyExistsException(fields);
        }

        return categoryRepository.save(category);
    }

    public Category update(long categoryId, Category category) throws ResourceAlreadyExistsException, ResourceNotFoundException{
        boolean categoryAlreadyExits = categoryRepository.findByName(category.getName()).isPresent();
        if(categoryAlreadyExits){
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("name", "Category name already exists"));
            throw new ResourceAlreadyExistsException(fields);
        }

        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("categoryId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });
        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }
}
