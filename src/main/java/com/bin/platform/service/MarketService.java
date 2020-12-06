package com.bin.platform.service;

import com.bin.platform.dto.OrderData;
import com.bin.platform.dto.TickData;
import com.bin.platform.model.Order;
import com.bin.platform.model.Tick;
import com.bin.platform.repository.OrderRepository;
import com.bin.platform.repository.TickRepository;
import com.bin.platform.repository.TraderRepository;
import com.bin.platform.util.Util;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarketService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private TickRepository tickRepository;

    public Tick saveTickData(Tick tick) {
        return tickRepository.save(tick);
    }

    public List<TickData> getLastData(int records, int timeFrame, String symbol) {
        List<TickData> tickDataList = new ArrayList<>();
        LocalDateTime from = LocalDateTime.now().minusSeconds(records).withNano(0);
        List<Tick> tickList = tickRepository.findAllByDateTimeAfterAndSymbol(from, symbol);
        for (Tick tick : tickList) {
            TickData tickData = new TickData(tick);
            tickData.setFill("Gray");
            tickData.setPriceDisabled(true);
            tickData.setAskDisabled(true);
            tickData.setBidDisabled(true);
            Order order = orderRepository.findOrderByTickAndTimeFrame(tick, timeFrame);
            if (order != null) {
                tickData.setRotation(order.getType().equals("CALL") ? 0 : 180);
                double upLine = (tickData.getAsk() + tickData.getPrice()) / 2;
                double downLine = (tickData.getBid() + tickData.getPrice()) / 2;
                if (order.getPrice() > upLine) {
                    tickData.setAskDisabled(false);
                } else if (order.getPrice() < downLine) {
                    tickData.setBidDisabled(false);
                } else {
                    tickData.setPriceDisabled(false);
                }
            }
            tickDataList.add(tickData);
        }
        return tickDataList;
    }

    public TickData getLastRecord(String symbol) {
        Tick tick = tickRepository.findTop1BySymbolOrderByDateTimeDesc(symbol);
        TickData tickData = new TickData(tick);
        tickData.setFill("Gray");
        tickData.setPriceDisabled(true);
        tickData.setAskDisabled(true);
        tickData.setBidDisabled(true);
        tickData.setRotation(0);
        return tickData;
    }

    public List<Order> getLatestOrders(int timeFrame, String symbol) {
        return orderRepository.findAllByDateTimeBetweenAndTimeFrameAndSymbol(LocalDateTime.now().minusSeconds(3 * timeFrame), LocalDateTime.now(), timeFrame, symbol);
    }

    public OrderData placeOrder(Order order) {
        System.out.println(" >>> Place Order: " + order.toString());
        Tick tick = tickRepository.findById(order.getTick().getId()).get();
        order.setTick(tick);
        if (order.isSpread()) {
            order.setPrice(order.getType().equals("CALL") ? tick.getAsk() : tick.getBid());
        } else {
            order.setPrice(tick.getPrice());
        }
        order.setDateTime(tick.getDateTime());
        order.setEndPrice(tick.getPrice());
        order.setEndDateTime(tick.getDateTime().plusSeconds(order.getTimeFrame()));
        order.setTrader(traderRepository.findById(1L).get());
        return new OrderData(orderRepository.save(order), tick);
    }

    public OrderData updateOrder(Order order) {
        order = orderRepository.findById(order.getId()).get();
        Tick tick = tickRepository.findTop1BySymbolOrderByDateTimeDesc(order.getSymbol());
        if(order.getEndDateTime().isAfter(tick.getDateTime())){
            order.setEndPrice(tick.getPrice());
            return new OrderData(orderRepository.save(order), tick);
        }
        return new OrderData(order, tick);
    }

    public void removeExtraData() {
        tickRepository.deleteAllByDateTimeBefore(LocalDateTime.now().minusDays(1));
    }
}
