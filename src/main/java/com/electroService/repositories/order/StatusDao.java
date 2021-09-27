package com.electroService.repositories.order;

import com.electroService.entitys.order.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusDao extends JpaRepository<Status, Long> {
}
