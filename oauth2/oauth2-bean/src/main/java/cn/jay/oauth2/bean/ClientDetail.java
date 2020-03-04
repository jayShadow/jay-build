package cn.jay.oauth2.bean;

import cn.jay.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Jay
 * @Date: 2020/1/13 16:15
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "t_client_detail")
public class ClientDetail extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String clientId;

    private Set<String> resourceIds = new HashSet<>();

    private boolean secretRequired;

    private String clientSecret;

    private boolean scoped;

    private Set<String> scope = new HashSet<>();

    private Set<String> authorizedGrantTypes = new HashSet<>();

    private Set<String> registeredRedirectUri = new HashSet<>();

    private Set<String> authorities = new HashSet<>();

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private Set<String> autoApproveScope = new HashSet<>();

    private String additionalInformation;

}
