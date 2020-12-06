package com.bin.platform.controller;

import com.bin.platform.dto.OrderData;
import com.bin.platform.dto.TickData;
import com.bin.platform.model.Order;
import com.bin.platform.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/market")
public class MarketController {
    @Autowired
    private MarketService marketService;

    @GetMapping("/lastData")
    public List<TickData> getLastData(@Param("records") Integer records, @Param("timeFrame") int timeFrame, @Param("symbol") String symbol) {
        return marketService.getLastData(records, timeFrame, symbol);
    }

    @GetMapping("/lastRecord")
    public TickData getLastRecord(@Param("symbol") String symbol) {
        return marketService.getLastRecord( symbol);
    }

    @GetMapping("/latestOrders")
    public List<Order> getLatestOrders(@Param("timeFrame") int timeFrame, @Param("symbol") String symbol) {
        return marketService.getLatestOrders( timeFrame, symbol);
    }

    @PostMapping("/placeOrder")
    public OrderData placeOrder(@RequestBody Order order) {
        return marketService.placeOrder(order);
    }

    @PostMapping("/updateOrder")
    public OrderData updateOrder(@RequestBody Order order) {
        return marketService.updateOrder(order);
    }
}
