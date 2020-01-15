package cn.jay.common.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: Jay
 * @Date: 2020/1/7 10:44
 */
@Data
@Accessors(chain = true)
public abstract class BaseTenantEntity extends BaseCommonEntity {

    @TableField(exist = false) // 保存时mybatis plus 自动添加该字段，故该字段，需要剔除实体中
    @ApiModelProperty(value = "租户编号", position = 8)
    private String tenantId;

}
