package com.capstone.wellnessnavigatorgym.service;

import com.capstone.wellnessnavigatorgym.entity.Account;

import java.util.Optional;

public interface IAccountService {
    Optional<Account> findAccountByUserName(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Account save(Account account);
}
