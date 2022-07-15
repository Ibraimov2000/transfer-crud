package com.example.cbk.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account recipient;

    @ManyToOne
    private Currency currency;

    @Column
    private String status;

    @Column
    private String comment;

    @Column(nullable = false)
    private Long amount;

    @Column
    private Integer unicCode;

    @ManyToOne
    private Cash cash;
}
