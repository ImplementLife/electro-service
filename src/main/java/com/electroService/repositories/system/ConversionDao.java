package com.electroService.repositories.system;

import com.electroService.entitys.system.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionDao extends JpaRepository<Conversion, Long> {
    List<Conversion> findAllByAllTime(boolean allTime);
}
