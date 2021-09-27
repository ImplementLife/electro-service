package com.electroService.services;

import com.electroService.entitys.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    private Map<String, User> users;
    private BCryptPasswordEncoder encoder;

    private void addUser(User user) {
        if (users == null) users = new HashMap<>();
        if (encoder == null) encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        users.put(user.getUsername(), user);
    }

    public UserService() {
        addUser(new User("admin", "c24m2021r", "ROLE_ADMIN"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.get(username);
    }
}
