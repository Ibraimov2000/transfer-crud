package com.example.cbk.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Random;


@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String number;

    @Column
    private Integer unicCode;

    @Column
    private Long transferId;

    public static Integer getCode() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }
}
