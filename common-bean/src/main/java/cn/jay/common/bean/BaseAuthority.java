package cn.jay.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Jay
 * @Date: 2020/1/7 13:39
 */
@Data
@Accessors(chain = true)
public class BaseAuthority extends BaseCommonEntity {

    /**
     * 权限编码
     */
    @ApiModelProperty(value = "权限编码", position = 11)
    private String code;

}
