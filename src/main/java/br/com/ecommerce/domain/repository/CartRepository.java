package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart_item WHERE cart_item.cart_id = ?1", nativeQuery = true)
    void deleteCartItemsByCartId(long cartId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE cart SET cart.total = 0.0 WHERE cart.id = ?1", nativeQuery = true)
    void updateTotalToZero(long cartId);
}
