package com.bin.platform.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tick")
public class Tick {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "ask", nullable = false)
    private Double ask;

    @Column(name = "bid", nullable = false)
    private Double bid;

    @Column(name = "symbol", nullable = false)
    private String symbol;
}
