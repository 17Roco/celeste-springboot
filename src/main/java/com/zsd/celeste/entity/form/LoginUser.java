package com.zsd.celeste.entity.form;

import com.zsd.celeste.entity.PO.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public Integer getUid(){
        return getUser().getUid();
    }
    @Override
    public String getPassword() {
        return getUser().getPassword();
    }

    @Override
    public String getUsername() {
        return getUser().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return getUser().getStatus() == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return getUser().getStatus() == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getUser().getStatus() == 1;
    }

    @Override
    public boolean isEnabled() {
        return getUser().getStatus() == 1;
    }
}
