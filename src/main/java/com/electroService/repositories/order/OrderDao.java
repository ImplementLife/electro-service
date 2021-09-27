package com.electroService.repositories.order;

import com.electroService.entitys.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Long> {
}
