package cn.jay.base.security.bean;

import cn.jay.base.common.bean.BaseCommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Jay
 * @Date: 2020/1/6 16:25
 */
@Data
@Accessors(chain = true)
public abstract class BaseLoginUser extends BaseCommonEntity {

    /**
     * 登陆用户名
     */
    @ApiModelProperty(value = "登陆用户名", position = 11)
    private String username;

    /**
     * 登陆密码
     */
    @ApiModelProperty(value = "登陆密码", position = 12)
    private String password;

}
