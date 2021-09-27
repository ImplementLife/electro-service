package com.electroService.controllers;

import com.electroService.entitys.system.Conversion;
import com.electroService.services.defaultRestData.NotFoundException;
import com.electroService.services.order.OrderService;
import com.electroService.services.order.StatusService;
import com.electroService.services.system.CountCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private CountCallService countCallService;

    public Conversion getCurrentMainPageConversion() {
        int mainCallCount = countCallService.getByName("Get main page").getCount();
        int createOrderCallCount = countCallService.getByName("Post create order").getCount();

        return new Conversion(mainCallCount, createOrderCallCount);
    }

    @GetMapping("/orders")
    public String getOrders(
            Model model,
            @RequestParam(required = false, defaultValue = "0") Long statusId
    ) {
        if (statusId == 0) model.addAttribute("orders", orderService.getAll());
        else model.addAttribute("orders", orderService.getByStatusId(statusId));

        model.addAttribute("statuses", statusService.getAll());
        model.addAttribute("selectedId", statusId);

        model.addAttribute("conversion", getCurrentMainPageConversion());

        return "admin/allOrders";
    }

    @GetMapping("/orders/{id}")
    public String getOrderInfo(Model model, @PathVariable Long id) throws NotFoundException {
        model.addAttribute("order", orderService.getById(id));
        model.addAttribute("statuses", statusService.getAll());
        return "admin/orderInfo";
    }

    @PostMapping("/orders")
    public String updateStatus(
            @RequestParam Long orderId,
            @RequestParam Long statusId
    ) throws NotFoundException {
        orderService.updateStatus(orderId, statusId);
        return "redirect:/admin/orders/" + orderId;
    }

    @PostMapping("/orders/addComment/{id}")
    public String addComment(@PathVariable Long id, @RequestParam String comment) throws NotFoundException {
        orderService.addComment(id, comment);
        return "redirect:/admin/orders/" + id;
    }

    @GetMapping("/orders/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderService.deleteById(id);
        return "redirect:/admin/orders";
    }
}
