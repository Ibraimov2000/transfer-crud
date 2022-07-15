package com.example.cbk.repository;

import com.example.cbk.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRepository extends JpaRepository<Cash, Long> {
}
