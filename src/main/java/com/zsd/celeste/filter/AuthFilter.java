package com.zsd.celeste.filter;

import com.zsd.celeste.entity.DO.LoginUser;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 验证
        String token = request.getHeader("token");
        String username = auth(token);
        // 输出访问的接口
        System.out.println(username+" : "+request.getMethod()+" : "+request.getRequestURI());
        //
        filterChain.doFilter(request,response);
    }
    String auth(String token){
        // 无 token
        if(!StringUtils.hasText(token))
            return "游客";
        // token 无效
        LoginUser user = service.getUser(token);
        if(Objects.isNull(user))
            throw new RuntimeException("token失效");
        // 保存到context
        Authentication t = new UsernamePasswordAuthenticationToken(user,token,null);
        SecurityContextHolder.getContext().setAuthentication(t);

        return user.getUsername();
    }
}
