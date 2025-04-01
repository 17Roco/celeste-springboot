package com.zsd.celeste.AOP;

import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.service.sys.TrafficService;
import com.zsd.celeste.util.AutUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class Traffic {

    @Autowired
    private TrafficService service;

    @Before("execution(* com.zsd.celeste.controller.*.*(..))")
    public void logTraffic(JoinPoint jp) {
//        LoginUser user = AutUtil.getAuthentication();
        service.addTraffic();
    }
}
