package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface IAccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "select account_id, user_name, encrypt_password, email, is_enable " +
            "from account " +
            "where user_name = :username and is_enable = true", nativeQuery = true)
    Optional<Account> findAccountByUserName(@Param("username") String username);

//    Boolean existsByUsername(String username);
//    Boolean existsByEmail(String email);
}
