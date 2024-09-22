package com.zsd.celeste.controller;

import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.TokenService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AutUtil autUtil;
//    @PostMapping("/login")
//    public Result login(@RequestBody User user){
//        if(Objects.isNull(user.getUsername()) || user.getUsername().isEmpty() || Objects.isNull(user.getPassword()) || user.getPassword().isEmpty())throw new RuntimeException("用户名或密码欠缺");
//        // 验证用户
//        Authentication authenticate = manager.authenticate(toAuthentication(user));
//        LoginUser u = (LoginUser) authenticate.getPrincipal();
//        // 判断用户认证
//        if (Objects.isNull(u))
//            throw new RuntimeException("用户认证失败");
//        String token = online.userLogin(u);
//        return Result.success(token);
//    }
    @PostMapping("/logout")
    @PreAuthorize("@autUtil.needLogin()")
    public Result logout(){
//        return Result.judge(online.userLogout(autUtil.getLoginUser().getToken()));
        return null;
    }




    /**
     * 将 user 封装成 Authentication
     */
    private Authentication toAuthentication(User user){
        return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
    }
}
