package com.example.cbk.service;

import com.example.cbk.entity.Transfer;
import com.example.cbk.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    @Autowired
    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public Transfer create(Transfer transfer) {
        return transferRepository.saveAndFlush(transfer);
    }

    @Override
    public void update(Transfer transfer, Long id) {
        Transfer transfer1 = transferRepository.getById(id);
        transfer1.setAmount(transfer.getAmount());
        transfer1.setComment(transfer.getComment());
        transfer1.setRecipient(transfer.getRecipient());
        transfer1.setSender(transfer.getSender());
        transfer1.setStatus(transfer.getStatus());
        transfer1.setUnicCode(transfer.getUnicCode());
        transferRepository.saveAndFlush(transfer1);
    }

    @Override
    public Transfer getById(Long id) {
        return transferRepository.findById(id).get();
    }

    @Override
    public List<Transfer> getAll() {
        return transferRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        transferRepository.deleteById(id);
    }

    @Override
    public List<Transfer> search(String keyword) {
        if (keyword != null) {
            return transferRepository.search(keyword);
        }
        return transferRepository.findAll();
    }
}
