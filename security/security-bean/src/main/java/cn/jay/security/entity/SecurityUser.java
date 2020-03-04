package cn.jay.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Jay
 * @Date: 2020/1/7 14:37
 */
public class SecurityUser implements UserDetails {

    private transient LoginUser loginUser;

    private transient Set<SecurityAuthority> securityAuthorities;

    public SecurityUser(LoginUser loginUser, Set<SecurityAuthority> securityAuthorities) {
        this.loginUser = loginUser;
        this.securityAuthorities = securityAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (!CollectionUtils.isEmpty(securityAuthorities)) {
            for (SecurityAuthority securityAuthority : securityAuthorities) {
                authorities.add(new SimpleGrantedAuthority(securityAuthority.getCode()));
            }
        }
        return authorities;
    }

    public LoginUser getLoginUser() {
        return this.loginUser;
    }

    @Override
    public String getPassword() {
        return this.loginUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.loginUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
