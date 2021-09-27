package com.electroService.services.system;

import com.electroService.entitys.system.Log;
import com.electroService.services.defaultRestData.DefaultRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class LogService extends DefaultRestServiceImpl<Log> {
}
