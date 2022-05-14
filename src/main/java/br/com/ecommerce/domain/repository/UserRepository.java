package br.com.ecommerce.domain.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ecommerce.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    //entityGraph findAll cart
    public Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(
        value = "DELETE FROM users_roles WHERE user_id = ?1",
        nativeQuery = true
    )
    public void deleteFromLinkTable(Long userId);
}
