package com.zsd.celeste.filter;

import com.zsd.celeste.entity.form.LoginUser;
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
        LoginUser user = auth(request.getHeader("token"));
        print(user,request);
        //
        filterChain.doFilter(request,response);
    }

    void print(LoginUser user,HttpServletRequest request){
        // 输出访问的接口
        String s = Objects.isNull(user)?"游客":user.getToken() + " :: " + user.getUsername();
        System.out.println(s+"    >>>>   "+request.getMethod()+" :: "+request.getRequestURI());
    }

    LoginUser auth(String token){
        // 无 token
        if(!StringUtils.hasText(token)) return null;
        // token 无效
        LoginUser user = service.getUser(token);
        if(Objects.isNull(user)) {
            //throw new RuntimeException("token失效");
            // todo
            user = devTokenMode(token);
        }
        // 保存到context
        Authentication t = new UsernamePasswordAuthenticationToken(user,token,null);
        SecurityContextHolder.getContext().setAuthentication(t);
        return user;
    }

    LoginUser devTokenMode(String token){
        if (token.startsWith("dev-")){
            User user = new User();
            user.setUid(Integer.valueOf(token.split("-")[1]));
            user.setUsername("dev user");
            return new LoginUser(user, token);
        }
        return null;
    }
}
