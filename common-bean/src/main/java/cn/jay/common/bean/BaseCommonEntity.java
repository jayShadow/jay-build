package cn.jay.common.bean;

import cn.jay.common.enumeration.BooleanType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Jay
 * @Date: 2020/1/6 16:38
 */
@Data
@Accessors(chain = true)
public abstract class BaseCommonEntity implements Serializable {

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 2)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", position = 3)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifiedAt;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", position = 4)
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", position = 5)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifiedBy;

    /**
     * 版本（乐观锁）
     */
    @ApiModelProperty(value = "版本（乐观锁）", position = 6)
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除", position = 7)
    @TableLogic(value = "0", delval = "1")
    private BooleanType isDelete;

}
