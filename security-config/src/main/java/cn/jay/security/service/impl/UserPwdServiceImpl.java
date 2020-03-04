package cn.jay.security.service.impl;

import cn.jay.security.entity.AbstractAuthority;
import cn.jay.security.entity.AbstractUser;
import cn.jay.security.entity.SecurityUser;
import cn.jay.security.mapper.LoginUserMapper;
import cn.jay.security.mapper.SecurityAuthorityMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Jay
 * @Date: 2020/1/7 13:53
 */
@Service("userPwdService")
public class UserPwdServiceImpl implements UserDetailsService {

    private final LoginUserMapper loginUserMapper;

    private final SecurityAuthorityMapper securityAuthorityMapper;

    public UserPwdServiceImpl(LoginUserMapper loginUserMapper, SecurityAuthorityMapper securityAuthorityMapper) {
        this.loginUserMapper = loginUserMapper;
        this.securityAuthorityMapper = securityAuthorityMapper;
    }

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<AbstractUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AbstractUser::getUsername, username);
        AbstractUser abstractUser = loginUserMapper.selectOne(queryWrapper);
        if (abstractUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new SecurityUser(abstractUser, loadUserAuthorities());
    }

    private Set<AbstractAuthority> loadUserAuthorities() {
        QueryWrapper<AbstractAuthority> queryWrapper = new QueryWrapper<>();
        return new HashSet<>(securityAuthorityMapper.selectList(queryWrapper));
    }

}
