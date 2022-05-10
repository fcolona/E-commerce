package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM image WHERE product_id = ?1", nativeQuery = true)
    void deleteByProductId(long productId);
}
