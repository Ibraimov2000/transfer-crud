package com.example.cbk.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
