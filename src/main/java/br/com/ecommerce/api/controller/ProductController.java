package br.com.ecommerce.api.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ecommerce.api.assembler.ProductAssembler;
import br.com.ecommerce.api.model.input.ProductInput;
import br.com.ecommerce.domain.model.Product;
import br.com.ecommerce.domain.repository.ProductRepository;
import br.com.ecommerce.domain.service.ProductService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {
   private final ProductRepository productRepository;
   private final ProductService productService;
   private final ProductAssembler productAssembler;
   
   @GetMapping
    public Page<Product> getProductPage(@PageableDefault(page = 0, size = 10, sort = "name", direction = Direction.DESC) Pageable pageable){
            return productRepository.findAll(pageable);
    } 

    @GetMapping("/search")
    public List<Map<String, Object>> getProductPageByCategories(@PageableDefault(page = 0, size = 10, sort = "name", direction = Direction.DESC) Pageable pageable, @RequestParam List<String> categories) throws SQLException{
       return productService.getProductsByCategories(pageable, categories); 
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductInput productInput){
        Product product = productAssembler.toEntity(productInput);

        return productService.save(product); 
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long productId){
        productRepository.deleteFromLinkTable(productId);
        productRepository.deleteById(productId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable long productId, @RequestBody ProductInput productInput){
        Product product = productAssembler.toEntity(productInput);

        return productService.update(productId, product); 
    }
}
