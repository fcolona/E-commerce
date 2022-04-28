package br.com.ecommerce.domain.service;

import org.springframework.stereotype.Service;

import br.com.ecommerce.domain.model.Category;
import br.com.ecommerce.domain.repository.CategoryRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    
    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public Category update(long categoryId, Category category) {
        //Check if new name is unique

        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow();
        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }
}
