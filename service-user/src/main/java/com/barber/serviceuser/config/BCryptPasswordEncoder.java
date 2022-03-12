package com.barber.serviceuser.config;

import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder extends org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder {
    public BCryptPasswordEncoder() {
        super();
    }
}
