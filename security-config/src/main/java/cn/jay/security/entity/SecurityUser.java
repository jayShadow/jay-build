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

    private transient AbstractUser abstractUser;

    private transient Set<AbstractAuthority> securityAuthorities;

    public SecurityUser(AbstractUser abstractUser, Set<AbstractAuthority> securityAuthorities) {
        this.abstractUser = abstractUser;
        this.securityAuthorities = securityAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (!CollectionUtils.isEmpty(securityAuthorities)) {
            for (AbstractAuthority abstractAuthority : securityAuthorities) {
                authorities.add(new SimpleGrantedAuthority(abstractAuthority.getCode()));
            }
        }
        return authorities;
    }

    public AbstractUser getUser() {
        return this.abstractUser;
    }

    @Override
    public String getPassword() {
        return this.abstractUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.abstractUser.getUsername();
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
