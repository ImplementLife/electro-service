package com.electroService.services.system;

import com.electroService.entitys.system.CountCall;
import com.electroService.repositories.system.CountCallDao;
import com.electroService.services.defaultRestData.DefaultRestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountCallService extends DefaultRestServiceImpl<CountCall> {
    @Autowired
    private CountCallDao dao;

    public CountCall getByName(String name) {
        return this.dao.findByName(name);
    }

    public boolean update(String name) {
        CountCall countCallInDB = dao.findByName(name);
        if (countCallInDB == null) {
            countCallInDB = new CountCall();
            countCallInDB.setName(name);
            countCallInDB.setCount(0);
            countCallInDB = dao.save(countCallInDB);
        }
        return super.update(countCallInDB.increment());
    }
}
