package com.bin.platform.repository;

import com.bin.platform.model.Order;
import com.bin.platform.model.Tick;
import com.bin.platform.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    Order findOrderByTickAndTimeFrame(Tick tick, int timeFrame);
    Order findOrderByDateTimeAndTraderAndTimeFrameAndSymbol(LocalDateTime date, Trader trader, int timeFrame, String symbol);
    List<Order> findAllByDateTimeBetweenAndTimeFrameAndSymbol(LocalDateTime from, LocalDateTime to, int timeFrame, String symbol);
}
