package com.electroService.controllers;

import com.electroService.entitys.order.Order;
import com.electroService.entitys.system.Log;
import com.electroService.services.TelegramService;
import com.electroService.services.defaultRestData.AlreadyExistException;
import com.electroService.services.order.OrderService;
import com.electroService.services.system.CountCallService;
import com.electroService.services.system.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CountCallService countCallService;

    @Autowired
    private LogService logService;

    @Autowired
    private TelegramService telegramService;

    @PostMapping("/order")
    @ResponseBody
    private ResponseEntity<Object> create(@Valid @RequestBody Order order) {
        try {
            orderService.create(order);
            telegramService.notificationAllSubscribers(order);
            countCallService.update("Post create order");
            log.info("Created new order");
        } catch (AlreadyExistException e) {
            countCallService.update("Fail create order");
            log.warn("Fail created new order");
            try {
                logService.create(new Log("Fail created new order."));
            } catch (AlreadyExistException alreadyExistException) {
                log.warn("exception save logDB");
            }
            return ResponseEntity.status(500).body("already exist");
        }
        return ResponseEntity.status(201).body("success");
    }

}
