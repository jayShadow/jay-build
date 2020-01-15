package security.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Jay
 * @Date: 2020/1/13 16:15
 */
@MappedTypes(Set.class)
public class StringSetTypeHandler extends BaseTypeHandler<Set<String>> {

    /** 分隔符 */
    private static final String SEPARATOR = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<String> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            return;
        }
        String join = CollectionUtils.isEmpty(parameter) ? null : String.join(SEPARATOR, parameter);
        ps.setString(i, join);
    }

    @Override
    public Set<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        return !StringUtils.hasText(string) ? null : Arrays.stream(string.split(SEPARATOR)).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String string = rs.getString(columnIndex);
        return !StringUtils.hasText(string) ? null : Arrays.stream(string.split(SEPARATOR)).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String string = cs.getString(columnIndex);
        return !StringUtils.hasText(string) ? null : Arrays.stream(string.split(SEPARATOR)).collect(Collectors.toSet());
    }
}
