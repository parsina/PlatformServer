package com.bin.platform.job;

import com.bin.platform.model.Tick;
import com.bin.platform.service.MarketService;
import com.bin.platform.util.Util;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class ScheduledTasks {

    private String path = "C:\\Users\\javad.farzaneh\\AppData\\Roaming\\MetaQuotes\\Terminal\\3294B546D50FEEDA6BF3CFC7CF858DB7\\MQL4\\Files\\TickData\\";

    @Autowired
    private MarketService marketService;

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        try (Stream<Path> paths = Files.walk(Paths.get(path + LocalDate.now().toString().replace('-', '.')))) {
            paths.filter(Files::isRegularFile).forEach(filePath -> {
                Tick tick = loadData(filePath);
                tick.setDateTime(LocalDateTime.now().withNano(0));
                marketService.saveTickData(tick);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Tick loadData(Path filePath){
        Tick tick = new Tick();
        tick.setSymbol(filePath.getFileName().toString().split("\\.")[0]);
        try {
            ReversedLinesFileReader reader = new ReversedLinesFileReader(filePath.toFile());
            String record = reader.readLine();
            System.out.println(" >>> " + record);
            reader.close();
            double ask = Double.parseDouble(record.split(" >>> ")[1]);
            double bid = Double.parseDouble(record.split(" >>> ")[2]);
            double price = (ask + bid) / 2;
            double diff = ask - price;
            tick.setAsk(Util.round(price + 0.5 * diff, places(ask, ask)));
            tick.setBid(Util.round(price - 0.5 * diff, places(bid,bid)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tick.setPrice(Util.round((tick.getAsk() + tick.getBid()) / 2, places(tick.getAsk(), tick.getBid())));
        return tick;
    }

    private int places(double ask, double bid){
        String askText = Double.toString(ask);
        String bidText = Double.toString(bid);
        return  askText.length() > bidText.length() ? askText.length() - askText.indexOf('.') - 1 : bidText.length() - bidText.indexOf('.') - 1;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void updateTickData() {
        marketService.removeExtraData();
    }
}
