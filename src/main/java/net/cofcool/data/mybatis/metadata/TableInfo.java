/*
 * Copyright 2019-2020 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.cofcool.data.mybatis.metadata;

import java.sql.JDBCType;
import java.util.Set;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

/**
 *
 * @author CofCool
 */
public interface TableInfo {

    ColumnInfo column(String columnName);

    Class<?> javaType();

    Set<ColumnInfo> allColumns();

    SqlTable table();

    String name();

    IdInfo id();

    interface ColumnInfo {

        String name();

        String property();

        Class<?> javaType();

        JDBCType jdbcType();

        <T> T readValue(Object entity);

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
