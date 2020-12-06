package com.bin.platform.controller;

import com.bin.platform.model.Trader;
import com.bin.platform.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class TraderController {
    @Autowired
    private TraderRepository traderRepository;

    @GetMapping("/traders")
    public List<Trader> getAllEmployees() {
        return traderRepository.findAll();
    }
}
