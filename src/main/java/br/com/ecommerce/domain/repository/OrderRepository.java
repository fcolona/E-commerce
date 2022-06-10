package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM `order` WHERE user_id = ?1", nativeQuery = true)
    List<Order> findByUserId(long userId);

    @Query(value = "SELECT * FROM `order` WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    Optional<Order> findByIdAndUserId(long orderId, long userId);
}
