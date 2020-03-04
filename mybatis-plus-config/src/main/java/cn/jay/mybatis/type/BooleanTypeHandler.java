package cn.jay.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Boolean.class)
public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

    private final static String TRUE_STRING = "Y";

    private final static String FALSE_STRING = "N";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Boolean bool, JdbcType jdbcType) throws SQLException {
        ps.setString(i, bool ? TRUE_STRING : FALSE_STRING);
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName).trim();
        return BooleanTypeHandler.valueOf(string);
    }

    @Override
    public Boolean getNullableResult(ResultSet rs, int index) throws SQLException {
        String string = rs.getString(index).trim();
        return BooleanTypeHandler.valueOf(string);
    }

    @Override
    public Boolean getNullableResult(CallableStatement cs, int index) throws SQLException {
        String string = cs.getString(index).trim();
        return BooleanTypeHandler.valueOf(string);
    }

    private final static Boolean valueOf(String str) throws SQLException {
        switch (str) {
            case TRUE_STRING:
                return Boolean.TRUE;
            case FALSE_STRING:
                return Boolean.FALSE;
            default:
                throw new SQLException("Boolean str incorrect");
        }
    }


}
