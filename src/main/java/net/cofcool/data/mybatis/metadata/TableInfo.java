package net.cofcool.data.mybatis.metadata;

import java.sql.JDBCType;
import java.util.Set;
import org.mybatis.dynamic.sql.SqlTable;

public interface TableInfo {

    ColumnInfo getColumn(String columnName);

    Class<?> getJavaType();

    Set<ColumnInfo> getAllColumns();

    SqlTable getTable();

    String getName();

    IdInfo getId();

    interface ColumnInfo {

        String getName();

        String getAlias();

        Class<?> getJavaType();

        JDBCType getJDBCType();

        default boolean insertable() {
            return true;
        }

        default boolean updatable() {
            return true;
        }

        default boolean isId() {
            return false;
        }

    }

    interface IdInfo extends ColumnInfo {

        String getGeneratedType();

        IdGenerator getGenerator();

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
