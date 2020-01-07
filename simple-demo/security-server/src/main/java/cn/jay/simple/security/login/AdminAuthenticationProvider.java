package cn.jay.simple.security.login;

import cn.jay.simple.security.bean.SecurityUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 *  <p> 自定义认证处理 </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/10/12 14:49
 */
@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("username error！");
        }
        if (!userDetails.getPassword().equals(password)) {
            throw new BadCredentialsException("password error！");
        }

//        boolean isValid = PasswordUtils.isValidPassword(password, userInfo.getPassword(), userInfo.getCurrentUserInfo().getSalt());
//        // 验证密码
//        if (!isValid) {
//            throw new BadCredentialsException("密码错误！");
//        }

//        // 前后端分离情况下 处理逻辑...
//        // 更新登录令牌
//        String token = PasswordUtils.encodePassword(String.valueOf(System.currentTimeMillis()), Constants.SALT);
//        // 当前用户所拥有角色代码
//        String roleCodes = userInfo.getRoleCodes();
        // 生成jwt访问令牌
//        String jwt = Jwts.builder()
//                // 用户角色
//                .claim(Constants.ROLE_LOGIN, roleCodes)
//                // 主题 - 存用户名
//                .setSubject(authentication.getName())
//                // 过期时间 - 30分钟
////                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
//                // 加密算法和密钥
//                .signWith(SignatureAlgorithm.HS512, Constants.SALT)
//                .compact();

//        User user = userMapper.selectById(userInfo.getCurrentUserInfo().getId());
//        user.setToken(token);
//        userMapper.updateById(user);
//        userInfo.getCurrentUserInfo().setToken(token);
        return new UsernamePasswordAuthenticationToken(userDetails.getLoginUser(), password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
