package com.capstone.wellnessnavigatorgym.service;

import com.capstone.wellnessnavigatorgym.entity.Account;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IAccountService {
    Optional<Account> findAccountByUserName(String username);
//    Boolean existsByUsername(String username);
//    Boolean existsByEmail(String email);
    Account addAccount(Account account);
}
