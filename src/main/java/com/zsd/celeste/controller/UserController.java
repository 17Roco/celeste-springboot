package com.zsd.celeste.controller;

import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * (User)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:43:08
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService service;
    @Autowired
    private AutUtil autUtil;




}

