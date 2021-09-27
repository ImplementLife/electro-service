package com.electroService.configs.log.methodsLog;

import com.electroService.services.system.CountCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Slf4j
@Component
public class Log implements ApplicationListener<ServletRequestHandledEvent> {

    @Autowired
    private CountCallService countCallService;

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        if (event.getRequestUrl().equals("/")) {
            countCallService.update("Get main page");
        }

        /*String[] sub = event.getRequestUrl().split("/");
        boolean b = sub.length >= 2
                && !sub[1].equals("img")
                && !sub[1].equals("styles")
                && !sub[1].equals("favicon.ico")
                && !sub[1].equals("js");*/
        if (event.getUserName() != null/* && !b*/) {
            log.info("User '{}' call method '{}'", event.getUserName(), event.getRequestUrl());
        }
        log.info("RequestUrl '{}'", event.getRequestUrl());
    }
}
