package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.Image;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Override
    @EntityGraph(
           type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"product"}
    )
    List<Image> findAll();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM image WHERE product_id = ?1", nativeQuery = true)
    void deleteByProductId(long productId);
}
