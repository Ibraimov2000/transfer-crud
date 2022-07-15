package com.example.cbk.service;

import com.example.cbk.entity.Cash;
import com.example.cbk.entity.Currency;

import java.util.List;

public interface CurrencyService {
    Currency create(Currency currency);
    void update(Currency currency, Long id);
    Currency getById(Long id);
    List<Currency> getAll();
    void deleteById(Long id);
}
