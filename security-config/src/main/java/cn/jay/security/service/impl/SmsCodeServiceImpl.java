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
        QueryWrapper<AbstractUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AbstractUser::getMobile, mobile);
        AbstractUser abstractUser = loginUserMapper.selectOne(queryWrapper);
        if (abstractUser == null) {
            throw new UsernameNotFoundException("手机号不存在");
        }
        return new SecurityUser(abstractUser, loadUserAuthorities());
    }

    private Set<AbstractAuthority> loadUserAuthorities() {
        QueryWrapper<AbstractAuthority> queryWrapper = new QueryWrapper<>();
        return new HashSet<>(securityAuthorityMapper.selectList(queryWrapper));
    }

}
