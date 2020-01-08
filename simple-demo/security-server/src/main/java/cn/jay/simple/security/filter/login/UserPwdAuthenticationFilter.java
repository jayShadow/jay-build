package cn.jay.simple.security.filter.login;

//import cn.jay.simple.security.login.CusAuthenticationManager;

import cn.jay.simple.security.token.UserPwdAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UserPwdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public UserPwdAuthenticationFilter() {
        super(new AntPathRequestMatcher("/common/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        if (username == null) {
            throw new InternalAuthenticationServiceException("Failed to get the username");
        }
        String password = request.getParameter("password");
        if (password == null) {
            throw new InternalAuthenticationServiceException("Failed to get the password");
        }
        return this.getAuthenticationManager().authenticate(new UserPwdAuthenticationToken(username, password));
    }


}
