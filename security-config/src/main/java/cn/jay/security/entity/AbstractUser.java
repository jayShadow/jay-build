package cn.jay.security.entity;

import cn.jay.common.entity.EditableEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: Jay
 * @Date: 2020/1/6 16:25
 * @Description: 登陆用户
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractUser extends EditableEntity {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登陆用户名
     */
    private String username;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 登陆手机号
     */
    private String mobile;

}
