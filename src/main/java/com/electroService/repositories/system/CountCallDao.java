package com.electroService.repositories.system;

import com.electroService.entitys.system.CountCall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountCallDao extends JpaRepository<CountCall, Long> {
    CountCall findByName(String name);
}
