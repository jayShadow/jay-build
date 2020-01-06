package cn.jay.base.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Jay
 * @Date: 2020/1/6 16:38
 */
public abstract class BaseEntity<T, PK extends Serializable> implements Serializable {

    /** 主键 */
    @ApiModelProperty(value = "主键", position = 1)
    private PK id;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 2)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 3)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifiedAt;

    /** 创建人 */
    @Column(length = 32)
    @ApiModelProperty(value = "创建人", position = 4)
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /** 更新人 */
    @Column(length = 32)
    @ApiModelProperty(value = "更新人", position = 5)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifiedBy;

    /** 版本（乐观锁） */
    @ApiModelProperty(value = "版本（乐观锁）", position = 6)
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    /** 逻辑删除 */
    @ApiModelProperty(value = "逻辑删除", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private BooleanType isDelete;

    public T setId(PK id) {
        this.id = id;
        return (T) this;
    }

    public T setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return (T) this;
    }

    public T setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
        return (T) this;
    }

    public T setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return (T) this;
    }

    public T setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return (T) this;
    }

    public T setVersion(Integer version) {
        this.version = version;
        return (T) this;
    }

    public T setIsDelete(BooleanType isDelete) {
        this.isDelete = isDelete;
        return (T) this;
    }

}
