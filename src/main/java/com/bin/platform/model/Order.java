package com.bin.platform.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "trader_id", nullable = false)
    private Trader trader;

    @Column(name = "order_type", nullable = false)
    private String type;

    @Column(name = "order_price", nullable = false)
    private double price;

    @Column(name = "order_end_price", nullable = false)
    private double endPrice;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "time_frame", nullable = false)
    private int timeFrame;

    @Column(name = "spread", nullable = false)
    private boolean spread;

    @Column(name = "order_symbol", nullable = false)
    private String symbol;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tick_id", referencedColumnName = "id", nullable = false)
    private Tick tick;
}
