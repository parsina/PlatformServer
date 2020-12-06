package com.bin.platform.dto;

import com.bin.platform.model.Order;
import com.bin.platform.model.Tick;
import com.bin.platform.model.Trader;
import com.bin.platform.util.Util;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderData {
    private long id;
    private Trader trader;
    private String type;
    private double price;
    private double endPrice;
    private Double ask;
    private Double bid;
    private LocalDateTime dateTime;
    private LocalDateTime endDateTime;
    private int timeFrame;
    private boolean spread;
    private String symbol;

    public OrderData() {
    }

    public OrderData(Order order, Tick tick) {
        this.id = order.getId();
        this.trader = order.getTrader();
        this.type = order.getType();
        this.price = order.getPrice();
        this.endPrice = order.getEndPrice();
        this.ask = Util.round(tick.getAsk(), 5);
        this.bid = Util.round(tick.getBid(), 5);
        this.dateTime = order.getDateTime();
        this.endDateTime = order.getEndDateTime();
        this.timeFrame = order.getTimeFrame();
        this.spread = order.isSpread();
        this.symbol = order.getSymbol();
    }
}
