package com.bin.platform.dto;

import com.bin.platform.model.Tick;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TickData {
    private long id;
    private LocalDateTime dateTime;
    private Double price;
    private Double ask;
    private Double bid;
    private boolean priceDisabled;
    private boolean askDisabled;
    private boolean bidDisabled;
    private String fill;
    private int rotation;

    public TickData() {
    }

    public TickData(Tick tick) {
        this.id = tick.getId();
        this.dateTime = tick.getDateTime();
        this.price = tick.getPrice();
        this.ask = tick.getAsk();
        this.bid = tick.getBid();
    }
}
