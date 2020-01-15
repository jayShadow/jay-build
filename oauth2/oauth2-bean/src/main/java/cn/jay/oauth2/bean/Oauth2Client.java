package cn.jay.oauth2.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class Oauth2Client implements ClientDetails {

    private transient ClientDetail clientDetail;

    public Oauth2Client(ClientDetail clientDetail) {
        this.clientDetail = clientDetail;
    }

    @Override
    public String getClientId() {
        return clientDetail.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return clientDetail.getResourceIds();
    }

    @Override
    public boolean isSecretRequired() {
        return clientDetail.isSecretRequired();
    }

    @Override
    public String getClientSecret() {
        return clientDetail.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return clientDetail.isScoped();
    }

    @Override
    public Set<String> getScope() {
        return clientDetail.getScope();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return clientDetail.getAuthorizedGrantTypes();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return clientDetail.getRegisteredRedirectUri();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return clientDetail.getAuthorities();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return clientDetail.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return clientDetail.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return CollectionUtils.isEmpty(clientDetail.getAutoApproveScope())? false: clientDetail.getAutoApproveScope().contains(scope);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
