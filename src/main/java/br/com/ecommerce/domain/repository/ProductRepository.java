package br.com.ecommerce.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    @Transactional
    @Modifying
    @Query(
        value = "DELETE FROM products_categories WHERE product_id = ?1",
        nativeQuery = true
    )
    public void deleteFromLinkTable(long productId);
}
