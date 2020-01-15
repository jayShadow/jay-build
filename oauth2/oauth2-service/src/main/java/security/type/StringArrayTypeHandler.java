package security.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Jay
 * @Date: 2020/1/13 16:15
 */
@MappedTypes(value = {Object[].class})
public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

    /** 分隔符 */
    private static final String SEPARATOR = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            return;
        }
        String join = String.join(SEPARATOR, parameter);
        join = StringUtils.hasText(join) ? join : null;
        ps.setString(i, join);
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        if (!StringUtils.hasText(string)) {
            return new String[0];
        }
        return string.split(SEPARATOR);
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String string = rs.getString(columnIndex);
        if (!StringUtils.hasText(string)) {
            return new String[0];
        }
        return string.split(SEPARATOR);
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String string = cs.getString(columnIndex);
        if (!StringUtils.hasText(string)) {
            return new String[0];
        }
        return string.split(SEPARATOR);
    }

}
