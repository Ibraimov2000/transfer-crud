package com.example.cbk.service;

import com.example.cbk.entity.Currency;
import com.example.cbk.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency create(Currency currency) {
        return currencyRepository.saveAndFlush(currency);
    }

    @Override
    public void update(Currency currency, Long id) {
        Currency currency1 = currencyRepository.getById(id);
        currency1.setName(currency.getName());
        currencyRepository.saveAndFlush(currency1);
    }

    @Override
    public Currency getById(Long id) {
        return currencyRepository.getById(id);
    }

    @Override
    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        currencyRepository.deleteById(id);
    }
}
