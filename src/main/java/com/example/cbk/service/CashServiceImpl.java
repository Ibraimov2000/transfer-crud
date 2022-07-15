package com.example.cbk.service;

import com.example.cbk.entity.Cash;
import com.example.cbk.repository.CashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashServiceImpl implements CashService {

    private final CashRepository cashRepository;

    @Autowired
    public CashServiceImpl(CashRepository cashRepository) {
        this.cashRepository = cashRepository;
    }

    @Override
    public Cash create(Cash cash) {
        return cashRepository.saveAndFlush(cash);
    }

    @Override
    public void update(Cash cash, Long id) {
        Cash cash1 = cashRepository.getById(id);
        cash1.setBalance(cash.getBalance());
        cashRepository.saveAndFlush(cash1);
    }

    @Override
    public Cash getById(Long id) {
        return cashRepository.findById(id).get();
    }

    @Override
    public List<Cash> getAll() {
        return cashRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        cashRepository.deleteById(id);
    }
}
