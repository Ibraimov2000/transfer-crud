package com.example.cbk.repository;


import com.example.cbk.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT t FROM Transfer t WHERE t.sender.username LIKE %?1%"
            + " OR t.sender.surname LIKE %?1%"
            + " OR t.recipient.username LIKE %?1%"
            + "OR t.recipient.surname LIKE %?1%"
            + "OR t.currency.name LIKE %?1%"
            + " OR t.status LIKE %?1%"
            + " OR CONCAT(t.amount, '') LIKE %?1%"
            + " OR CONCAT(t.unicCode, '') LIKE %?1%"
            + " OR CONCAT(t.cash.id, '') LIKE %?1%")
    public List<Transfer> search(String keyword);
}
