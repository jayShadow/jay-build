package cn.jay.security.filter.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Jay
 * @Date: 2020/1/3 9:50
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationTokenFilter(@Qualifier("userPwdService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (!StringUtils.isEmpty(token)) {
//            String username = jwtTokenUtil.getUsernameFromToken(token);
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                SecurityUser loadedUser = (SecurityUser) userDetailsService.loadUserByUsername(username);
//                if (jwtTokenUtil.validateToken(token, loadedUser)) {
//                    UserPwdAuthenticationToken authentication = new UserPwdAuthenticationToken(loadedUser.getLoginUser(), null, loadedUser.getAuthorities());
////                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
        chain.doFilter(request, response);
    }

}
