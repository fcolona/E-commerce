package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"orderItems", "userAddress", "orderItems.product", "orderItems.product.categories", "orderItems.product.images"}
    )
    @Query(value = "SELECT o FROM Order o WHERE o.userId = ?1")
    List<Order> findByUserId(long userId);

    @Query(value = "SELECT o FROM Order o WHERE o.id = :orderId AND o.userId = :userId")
    Optional<Order> findByIdAndUserId(long orderId, long userId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"orderItems", "orderItems.product", "orderItems.product.categories", "orderItems.product.images"}
    )
    @Query(value = "SELECT o FROM Order o WHERE o.id = :orderId AND o.userId = :userId")
    Optional<Order> findByIdAndUserIdAndRetrieveItems(long orderId, long userId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"userAddress"}
    )
    @Query(value = "SELECT o FROM Order o WHERE o.id = :orderId AND o.userId = :userId")
    Optional<Order> findByIdAndUserIdAndRetrieveAddress(long orderId, long userId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"orderItems", "userAddress", "orderItems.product", "orderItems.product.categories", "orderItems.product.images"}
    )
    @Query(value = "SELECT o FROM Order o WHERE o.id = :orderId AND o.userId = :userId")
    Optional<Order> findByIdAndUserIdAndRetrieveItemsAndAddress(long orderId, long userId);
}
