package com.example.cbk.service;

import com.example.cbk.entity.Cash;

import java.util.List;

public interface CashService {
    Cash create(Cash cash);
    void update(Cash cash, Long id);
    Cash getById(Long id);
    List<Cash> getAll();
    void deleteById(Long id);
}
