package com.zsd.celeste.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MainPasswordEncoder extends BCryptPasswordEncoder {
}
