package com.example.cbk.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
public class Cash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long balance;
}
