package com.electroService.configs.log.authLog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthBadCreditLog implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) event.getSource();
        log.info("User: '{}' -> try login.", authToken.getPrincipal().toString());
    }
}
