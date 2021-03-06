package cn.jay.security.filter.login;

import cn.jay.security.token.SmsCodeAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Jay
 * @Date: 2020/1/8 10:43
 */
@Slf4j
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/sms/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String mobile = request.getParameter("mobile");
        if (mobile == null) {
            throw new InternalAuthenticationServiceException("Failed to get the mobile");
        }
        String smscode = request.getParameter("smscode");
        if (smscode == null) {
            throw new InternalAuthenticationServiceException("Failed to get the smscode");
        }
        return this.getAuthenticationManager().authenticate(new SmsCodeAuthenticationToken(mobile, smscode));
    }

}
