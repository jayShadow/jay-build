package cn.jay.simple.security.bean;

import cn.jay.base.security.bean.BaseLoginUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: Jay
 * @Date: 2020/1/6 16:25
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "t_login_user")
@ApiModel(value = "登陆用户", description = "登陆用户")
public class LoginUser extends BaseLoginUser {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

    @ApiModelProperty(value = "手机号", position = 13)
    private String mobile;

}
