package com.example.demo.framework.mybatis.type;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author JiakunXu
 */
public class WildcardTypeHanlderCallback implements TypeHandler<String> {

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter,
                             JdbcType jdbcType) throws SQLException {
        if (StringUtils.isBlank(parameter)) {
            ps.setString(i, "%");
        } else {
            parameter = parameter.replace("%", "\\%");
            parameter = parameter.replace("_", "\\_");

            ps.setString(i, "%" + parameter + "%");
        }
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }

}
