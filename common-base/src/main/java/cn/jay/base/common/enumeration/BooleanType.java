package cn.jay.base.common.enumeration;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @Author: Jay
 * @Date: 2020/1/7 10:23
 */
public enum BooleanType implements IEnum<Integer> {

    FALSE,
    TRUE;

    /**
     * 当前状态，取反
     *
     * @return 枚举
     */
    public BooleanType negate() {
        return this == FALSE ? TRUE : FALSE;
    }

    /**
     * @return 结果
     * @see cn.jay.base.common.enumeration.BooleanType#TRUE is true
     */
    public boolean getBoolean() {
        return TRUE.equals(this);
    }

    @Override
    public Integer getValue() {
        return ordinal();
    }
}
