package cn.jay.oauth2.bean;

import cn.jay.common.bean.BaseCommonEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "客户端", description = "客户端")
public class ClientDetail extends BaseCommonEntity {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

    @ApiModelProperty(value = "客户端Id", position = 13)
    private String clientId;

    @ApiModelProperty(value = "客户端对应资源ID", position = 14)
    private Set<String> resourceIds = new HashSet<>();

    @ApiModelProperty(value = "是否需要密码", position = 15)
    private boolean secretRequired;

    @ApiModelProperty(value = "客户端密码", position = 16)
    private String clientSecret;

    @ApiModelProperty(value = "scoped", position = 17)
    private boolean scoped;

    @ApiModelProperty(value = "scope", position = 18)
    private Set<String> scope = new HashSet<>();

    @ApiModelProperty(value = "授权方式(授权码，简易，密码，客户端凭据)", position = 19)
    private Set<String> authorizedGrantTypes = new HashSet<>();

    @ApiModelProperty(value = "注册转跳uri", position = 20)
    private Set<String> registeredRedirectUri = new HashSet<>();

    @ApiModelProperty(value = "权限", position = 21)
    private Set<String> authorities = new HashSet<>();

    @ApiModelProperty(value = "token有效时间", position = 22)
    private Integer accessTokenValiditySeconds;

    @ApiModelProperty(value = "刷新token有效时间", position = 23)
    private Integer refreshTokenValiditySeconds;

    @ApiModelProperty(value = "自动同意scope", position = 24)
    private Set<String> autoApproveScope = new HashSet<>();

    @ApiModelProperty(value = "额外信息", position = 25)
    private String additionalInformation;

}
