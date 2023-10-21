package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.entity.Account;
import com.capstone.wellnessnavigatorgym.repository.IAccountRepository;
import com.capstone.wellnessnavigatorgym.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public Optional<Account> findAccountByUserName(String username) {
        return accountRepository.findAccountByUserName(username);
    }

//    @Override
//    public Boolean existsByUsername(String username) {
//        return accountRepository.existsByUsername(username);
//    }

//    @Override
//    public Boolean existsByEmail(String email) {
//        return accountRepository.existsByEmail(email);
//    }

    @Override
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }
}
