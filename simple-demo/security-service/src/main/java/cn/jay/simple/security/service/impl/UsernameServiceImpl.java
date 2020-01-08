package cn.jay.simple.security.service.impl;

import cn.jay.simple.security.bean.LoginUser;
import cn.jay.simple.security.bean.SecurityAuthority;
import cn.jay.simple.security.bean.SecurityUser;
import cn.jay.simple.security.mapper.LoginUserMapper;
import cn.jay.simple.security.mapper.SecurityAuthorityMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Jay
 * @Date: 2020/1/7 13:53
 */
@Component
public class UsernameServiceImpl implements UserDetailsService {

    private final LoginUserMapper loginUserMapper;

    private final SecurityAuthorityMapper securityAuthorityMapper;

    public UsernameServiceImpl(LoginUserMapper loginUserMapper, SecurityAuthorityMapper securityAuthorityMapper) {
        this.loginUserMapper = loginUserMapper;
        this.securityAuthorityMapper = securityAuthorityMapper;
    }

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<LoginUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LoginUser::getUsername, username);
        LoginUser loginUser = loginUserMapper.selectOne(queryWrapper);
        if (loginUser != null) {
            return new SecurityUser(loginUser, loadUserAuthorities());
        }
        return null;
    }

    private Set<SecurityAuthority> loadUserAuthorities() {
        QueryWrapper<SecurityAuthority> queryWrapper = new QueryWrapper<>();
        return new HashSet<>(securityAuthorityMapper.selectList(queryWrapper));
    }

}
