package com.electroService.repositories.system;

import com.electroService.entitys.system.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogDao extends JpaRepository<Log, Long> {
}
