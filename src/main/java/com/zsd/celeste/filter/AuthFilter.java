package com.zsd.celeste.filter;

import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.exception.exception.TokenEx;
import com.zsd.celeste.service.AuthService;
import com.zsd.celeste.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private AuthService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 验证
        LoginUser user = auth(request.getHeader("token"));
        print(user,request);
        //
        filterChain.doFilter(request,response);
    }



    // 输出日志
    void print(LoginUser user,HttpServletRequest request){
        // 输出访问的接口
        String msg = Objects.isNull(user)?"游客":user.getToken().substring(0,8) + "... :: " + user.getUsername();
        log.info("{} :: {} :: {}",
                msg,
                request.getMethod(),
                request.getRequestURI()
        );

    }


    // 验证 token
    LoginUser auth(String token){
        // 无 token
        if(!StringUtils.hasText(token)) return null;
        // 获取 user
        User u = service.getLoginUser(token);
        LoginUser user = new LoginUser(u,token);
        // 无效 token
        if(Objects.isNull(u)) {
//            throw new TokenEx();
            return null;
        }
        // 保存到context
        Authentication t = new UsernamePasswordAuthenticationToken(user,token,null);
        SecurityContextHolder.getContext().setAuthentication(t);
        return user;
    }

}
