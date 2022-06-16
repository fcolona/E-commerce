package br.com.ecommerce.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.domain.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    @Override
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"categories", "images"}
    )
    Page<Product> findAll(Pageable pageable);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"categories"}
    )
    @Query(value = "SELECT p FROM Product p WHERE p.id = ?1")
    Optional<Product> findByIdAndRetrieveCategories(long productId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"images"}
    )
    @Query(value = "SELECT p FROM Product p WHERE p.id = ?1")
    Optional<Product> findByIdAndRetrieveImages(long productId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"categories", "images"}
    )
    @Query(value = "SELECT p FROM Product p WHERE p.id = ?1")
    Optional<Product> findByIdAndRetrieveCategoriesAndImages(long productId);

    @Transactional
    @Modifying
    @Query(
        value = "DELETE FROM products_categories WHERE product_id = ?1",
        nativeQuery = true
    )
    void deleteFromLinkTable(long productId);
}
