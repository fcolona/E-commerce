package br.com.ecommerce.domain.service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import br.com.ecommerce.api.exception.ErrorDetails;
import br.com.ecommerce.api.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.com.ecommerce.domain.model.Product;
import br.com.ecommerce.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final JdbcTemplate jdbcTemplate;
   
    @Transactional
    public Product save(Product product){
        return productRepository.save(product);
    }

    public List<Map<String, Object>> getProductsByCategories(Pageable pageable, List<String> categoryNames) throws SQLException{
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT product.product_id, product.name, product.quantity FROM product");
        sb.append(" JOIN products_categories ON product.product_id = products_categories.product_id JOIN category ON category.category_id = products_categories.category_id WHERE");

        categoryNames.forEach( categoryName -> {
            sb.append(" category.name = '" + categoryName + "' OR"); 
        });
        sb.delete(sb.lastIndexOf("OR"), sb.length());
        sb.append("GROUP BY product.product_id");
        sb.append(" ORDER BY ");

        Stream<Sort.Order> sort = pageable.getSort().get();
        sort.forEach( item -> {
            sb.append(item.getProperty());
            sb.append(" ");
            sb.append(item.getDirection().name());
        });

        sb.append(" LIMIT ");
        sb.append(pageable.getPageSize());
        sb.append(" OFFSET ");
        sb.append(pageable.getPageNumber() * pageable.getPageSize());

        return jdbcTemplate.queryForList(sb.toString());
    }

    public Product update(long productId, Product product) throws ResourceNotFoundException {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> {
            Set<ErrorDetails.Field> fields = new HashSet<>();
            fields.add(new ErrorDetails.Field("productId", "Id given do not match"));
            throw new ResourceNotFoundException(fields);
        });

        //Check if the client have sent the propertie, if so, it is set in the existing product
        //if no, it just remains the same
        existingProduct.setName(Objects.isNull(product.getName()) ? existingProduct.getName() : product.getName()); 
        existingProduct.setPrice(Objects.isNull(product.getPrice()) ? existingProduct.getPrice() : product.getPrice()); 
        existingProduct.setQuantity(Objects.isNull(product.getQuantity()) ? existingProduct.getQuantity() : product.getQuantity()); 
        existingProduct.setCategories(Objects.isNull(product.getCategories()) ? existingProduct.getCategories() : product.getCategories()); 

        return productRepository.save(existingProduct);
    };
}
