package com.example.cbk.service;

import com.example.cbk.entity.Account;

import java.util.List;

public interface AccountService {

    Account create(Account account);
    void update(Account account, Long id);
    Account getById(Long id);
    List<Account> getAll();
    void deleteById(Long id);
}
