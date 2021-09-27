package com.electroService.quartz;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzSubmitJobs {
    private static final String CRON_EVERY_HOURS = "0 0 * ? * * *";
    private static final String CRON_EVERY_DAY_17_00 = "0 0 17 ? * * *";

    @Autowired
    private QuartzCreator quartzCreator;

    @Bean(name = "execute")
    public JobDetailFactoryBean jobMemberStats() {
        return quartzCreator.createJobDetail(UpdaterConversion.class, "Update RequestPay status");
    }
    
    /*
    @Bean(name = "exeTrigger")
    public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("execute") JobDetail jobDetail) {
        return quartzCreator.createTrigger(jobDetail, 60000, "Exe Trigger");
    }
    */

    @Bean(name = "exeTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("execute") JobDetail jobDetail) {
        return quartzCreator.createCronTrigger(jobDetail, CRON_EVERY_HOURS, "Exe Trigger");
    }
}
