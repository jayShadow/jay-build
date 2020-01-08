package cn.jay.simple.security.bean;

import cn.jay.base.security.bean.BaseAuthority;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: Jay
 * @Date: 2020/1/7 13:39
 */
@Getter
@Setter
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName(value = "t_security_authority")
@ApiModel(value = "操作权限", description = "操作权限")
public class SecurityAuthority extends BaseAuthority {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", position = 1)
    private Long id;

}
