package cn.jay.security.service.impl;

import cn.jay.security.bean.LoginUser;
import cn.jay.security.bean.SecurityAuthority;
import cn.jay.security.bean.SecurityUser;
import cn.jay.security.mapper.LoginUserMapper;
import cn.jay.security.mapper.SecurityAuthorityMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Jay
 * @Date: 2020/1/8 11:41
 */
@Service("smsCodeService")
public class SmsCodeServiceImpl implements UserDetailsService {

    private final LoginUserMapper loginUserMapper;

    private final SecurityAuthorityMapper securityAuthorityMapper;

    public SmsCodeServiceImpl(LoginUserMapper loginUserMapper, SecurityAuthorityMapper securityAuthorityMapper) {
        this.loginUserMapper = loginUserMapper;
        this.securityAuthorityMapper = securityAuthorityMapper;
    }

    @Override
    public SecurityUser loadUserByUsername(String mobile) throws UsernameNotFoundException {
        QueryWrapper<LoginUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LoginUser::getMobile, mobile);
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
