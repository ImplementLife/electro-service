package com.electroService.quartz;

import com.electroService.entitys.system.Conversion;
import com.electroService.repositories.system.ConversionDao;
import com.electroService.services.system.CountCallService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@DisallowConcurrentExecution
public class UpdaterConversion implements Job {

    @Autowired
    private CountCallService countCallService;

    @Autowired
    private ConversionDao conversionDao;

    public Conversion getLastAllTime(boolean allTime) {
        List<Conversion> all = conversionDao.findAllByAllTime(allTime);
        if (all.size() == 0) return null;
        all.sort((c1, c2) -> (int) (c1.getId() - c2.getId()));
        return all.get(all.size()-1);
    }

    @Override
    public void execute(JobExecutionContext context) {
        try {
            Date date = new Date();
            Conversion lastSavedAllTime = this.getLastAllTime(true);
            int mainCallCount = countCallService.getByName("Get main page").getCount();
            int createOrderCallCount = countCallService.getByName("Post create order").getCount();
            if (lastSavedAllTime == null) {
                lastSavedAllTime = new Conversion(mainCallCount, createOrderCallCount);
                lastSavedAllTime.setDate(date);
                lastSavedAllTime.setAllTime(true);
                conversionDao.saveAndFlush(lastSavedAllTime);
                lastSavedAllTime.setId(null);
                lastSavedAllTime.setAllTime(false);
                conversionDao.saveAndFlush(lastSavedAllTime);
                return;
            }

            Conversion allTime = new Conversion(mainCallCount, createOrderCallCount);
            allTime.setDate(date);
            allTime.setAllTime(true);

            Conversion lastTime = new Conversion();
            lastTime.setCountView(mainCallCount - lastSavedAllTime.getCountView());
            lastTime.setCountOrders(createOrderCallCount - lastSavedAllTime.getCountOrders());
            lastTime.mathConverse();
            lastTime.setDate(date);
            lastTime.setAllTime(false);

            conversionDao.saveAndFlush(allTime);
            conversionDao.saveAndFlush(lastTime);
        } finally {
            log.info("Job '{}' completed.", context.getJobDetail().getKey().getName());
        }
    }
}