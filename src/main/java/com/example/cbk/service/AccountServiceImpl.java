package com.example.cbk.service;

import com.example.cbk.entity.Account;
import com.example.cbk.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account create(Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @Override
    public void update(Account account, Long id) {
        Account account1 = accountRepository.getById(id);
        account1.setName(account.getName());
        account1.setSurname(account.getSurname());
        account1.setNumber(account.getNumber());
        account1.setUnicCode(account.getUnicCode());
        accountRepository.saveAndFlush(account1);
    }


    @Override
    public Account getById(Long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

}
