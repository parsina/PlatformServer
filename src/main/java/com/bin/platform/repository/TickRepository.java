package com.bin.platform.repository;

import com.bin.platform.model.Order;
import com.bin.platform.model.Tick;
import com.bin.platform.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TickRepository extends JpaRepository<Tick, Long>{
    List<Tick> findAllByDateTimeAfterAndSymbol(LocalDateTime date, String symbol);
    Tick findTop1BySymbolOrderByDateTimeDesc(String symbol);
    void deleteAllByDateTimeBefore(LocalDateTime dateTime);
}
