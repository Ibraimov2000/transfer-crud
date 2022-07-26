package com.example.cbk.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

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

    @Column
    @CreationTimestamp
    private LocalDateTime created;
}
