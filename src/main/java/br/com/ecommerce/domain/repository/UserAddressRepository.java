package br.com.ecommerce.domain.repository;

import br.com.ecommerce.domain.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    @Query(name = "SELECT a FROM UserAddress a WHERE User.id = ?1")
    List<UserAddress> findByUserId(long userId);

    @Query(name = "SELECT * FROM user_address WHERE address_id = ?1 AND user_id = ?2", nativeQuery = true)
    Optional<UserAddress> findByIdAndUserId(long addressId, long userId);

    @Transactional
    @Modifying
    @Query(name = "DELETE FROM user_address WHERE address_id = ?1 AND user_id = ?2", nativeQuery = true)
    void deleteByIdAndUserId(long addressId, long userId);
}
