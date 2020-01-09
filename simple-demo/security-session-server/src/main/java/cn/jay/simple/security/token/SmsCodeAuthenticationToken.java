package cn.jay.simple.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: Jay
 * @Date: 2020/1/8 10:46
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private final String mobile;

    private Object credentials;

    public SmsCodeAuthenticationToken(String mobile, Object credentials) {
        super(null);
        this.principal = null;
        this.mobile = mobile;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public SmsCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.mobile = null;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getMobile() {
        return this.mobile;
    }

}
