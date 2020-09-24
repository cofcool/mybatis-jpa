package net.cofcool.data.mybatis.metadata;

import java.sql.JDBCType;
import java.util.Set;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public interface TableInfo {

    ColumnInfo column(String columnName);

    Class<?> javaType();

    Set<ColumnInfo> allColumns();

    SqlTable table();

    String name();

    IdInfo id();

    interface ColumnInfo {

        String name();

        Class<?> javaType();

        JDBCType jdbcType();

        default boolean insertable() {
            return true;
        }

        default boolean updatable() {
            return true;
        }

        default boolean isId() {
            return false;
        }

        SqlColumn<?> sqlColumn();

    }

    interface IdInfo extends ColumnInfo {

        String generatedType();

        String generator();

        @Override
        default boolean isId() {
            return true;
        }

        @Override
        default boolean updatable() {
            return false;
        }
    }

}
