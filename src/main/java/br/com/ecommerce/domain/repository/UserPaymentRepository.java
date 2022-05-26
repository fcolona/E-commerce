package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.UserAddress;
import br.com.ecommerce.domain.model.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {

    @Query(name = "SELECT * FROM user_payment WHERE user_id = ?1", nativeQuery = true)
    List<UserPayment> findByUserId(long userId);

    @Transactional
    @Modifying
    @Query(name = "DELETE FROM user_payment WHERE address_id = ?1 AND user_id = ?2", nativeQuery = true)
    void deleteByIdAndUserId(long paymentId, long userId);

    @Query(name = "SELECT * FROM user_payment WHERE address_id = ?1 AND user_id = ?2", nativeQuery = true)
    Optional<UserPayment> findByIdAndUserId(long paymentId, long userId);
}
