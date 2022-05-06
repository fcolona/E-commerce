package br.com.ecommerce.api.controller;

import java.util.List;

import br.com.ecommerce.api.exception.ResourceAlreadyExistsException;
import br.com.ecommerce.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.domain.model.Category;
import br.com.ecommerce.domain.repository.CategoryRepository;
import br.com.ecommerce.domain.service.CategoryService;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody @Valid Category category) throws ResourceAlreadyExistsException {
       return categoryService.save(category); 
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable long categoryId, @RequestBody @Valid Category category) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        return categoryService.update(categoryId, category);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long categoryId){
        categoryRepository.deleteById(categoryId);

        return ResponseEntity.noContent().build();
    }
}
