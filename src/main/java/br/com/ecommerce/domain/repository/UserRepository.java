package br.com.ecommerce.domain.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Override
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"roles", "addresses", "orders", "orders.orderItems", "orders.orderItems.product", "orders.orderItems.product.categories", "orders.orderItems.product.images"}
    )
    List<User> findAll();

    @Override
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user WHERE user.user_id = ?1", nativeQuery = true)
    void deleteById(Long aLong);

    Optional<User> findByEmail(String email);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"roles"}
    )
    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailAndRetrieveRoles(String email);

    @Transactional
    @Modifying
    @Query(
        value = "DELETE FROM users_roles WHERE user_id = ?1",
        nativeQuery = true
    )
    void deleteFromLinkTable(Long userId);

    @Query(value = "DELETE FROM cart WHERE cart.id = ?1", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteCartByUserId(Long userId);
}
