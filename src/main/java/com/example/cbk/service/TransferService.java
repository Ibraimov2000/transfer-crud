package com.example.cbk.service;

import com.example.cbk.entity.Transfer;

import java.util.List;

public interface TransferService {
    Transfer create(Transfer transfer);
    void update(Transfer transfer, Long id);
    Transfer getById(Long id);
    List<Transfer> getAll();
    void deleteById(Long id);
    List<Transfer> search(String keyword);
}
