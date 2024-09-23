package com.zsd.celeste.config;

import com.zsd.celeste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityUtilConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserService userService;

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder encoder){
        // 创建 AuthenticationManager
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(userService);
        ProviderManager manager = new ProviderManager(provider);
        // 设置 userService
        userService.setPasswordEncoder(encoder);
        userService.setManager(manager);
        // 返回 AuthenticationManager
        return manager;
    }
}
