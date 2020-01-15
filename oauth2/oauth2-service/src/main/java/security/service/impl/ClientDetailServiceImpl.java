package security.service.impl;

import cn.jay.oauth2.bean.ClientDetail;
import cn.jay.oauth2.bean.Oauth2Client;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import security.mapper.ClientDetailMapper;

@Service("clientDetailsService")
public class ClientDetailServiceImpl implements ClientDetailsService {

    private final ClientDetailMapper clientDetailMapper;

    public ClientDetailServiceImpl(ClientDetailMapper clientDetailMapper) {
        this.clientDetailMapper = clientDetailMapper;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        QueryWrapper<ClientDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClientDetail::getClientId, clientId);
        ClientDetail clientDetail = clientDetailMapper.selectOne(queryWrapper);
        if (clientDetail != null) {
            return new Oauth2Client(clientDetail);
        }
        return null;
    }

}
