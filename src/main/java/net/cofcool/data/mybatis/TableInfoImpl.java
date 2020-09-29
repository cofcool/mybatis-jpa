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

package net.cofcool.data.mybatis;

import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.cofcool.data.mybatis.metadata.NamingStrategy;
import net.cofcool.data.mybatis.metadata.TableInfo;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

/**
 *
 * @author CofCool
 */
@ToString
public class TableInfoImpl implements TableInfo {

    private final Class<?> type;
    private String tableName;
    private SqlTable sqlTable;
    private final NamingStrategy namingStrategy;
    private IdInfo idInfo;

    private final Map<String, ColumnInfo> columnInfos = new HashMap<>();


    public TableInfoImpl(Class<?> type, NamingStrategy namingStrategy) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(namingStrategy);
        this.type = type;
        this.namingStrategy = namingStrategy;

        parsingEntity();
        parsingColumn();
    }

    public ColumnInfo column(String columnName) {
        ColumnInfo columnInfo = columnInfos.get(columnName);
        if (columnInfo == null) {
            throw new ColumnNotFoundException(String.format("The %s table can not find %s column", tableName, columnName));
        }

        return columnInfo;
    }

    @Override
    public Class<?> javaType() {
        return type;
    }

    @Override
    public Set<ColumnInfo> allColumns() {
        return new HashSet<>(columnInfos.values());
    }

    @Override
    public SqlTable table() {
        return sqlTable;
    }

    @Override
    public String name() {
        return tableName;
    }

    @Override
    public IdInfo id() {
        return idInfo;
    }

    private void parsingEntity() {
        Table table = type.getAnnotation(Table.class);
        String tableName = table.name();
        if (tableName.isEmpty()) {
            tableName = namingStrategy.physicalName(type.getSimpleName());
        }

        String schema = table.schema();
        if (!schema.isEmpty()) {
            tableName = schema + "." + tableName;
        }

        String catalog = table.catalog();
        if (!catalog.isEmpty()) {
            tableName = catalog + "." + tableName;
        }

        sqlTable = SqlTable.of(tableName);
        this.tableName = tableName;
    }

    private void parsingColumn() {
        boolean hasId = false;
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(Transient.class) != null) {
                continue;
            }

            String name = field.getName();
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                name = column.name();
            }
            name = namingStrategy.physicalName(name);

            ColumnInfoImpl columnInfo = new ColumnInfoImpl();
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                IdInfoImpl idInfoImpl = new IdInfoImpl();

                GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
                if (generatedValue != null) {
                    idInfoImpl
                        .generatedType(generatedValue.strategy().name())
                        .generator(generatedValue.generator());
                }
                hasId = true;
                idInfo = idInfoImpl;
                columnInfo = idInfoImpl;
            }
            columnInfo
                .javaType(field.getType())
                .name(name)
                .readField(field);

            columnInfos.put(name, columnInfo);
        }

        if (!hasId) {
            throw new IllegalStateException("The entity must have primary key");
        }
    }

    @Setter
    @Getter
    @Accessors(chain = true, fluent = true)
    @ToString
    class ColumnInfoImpl implements ColumnInfo {

        private String name;
        private Class<?> javaType;
        private JDBCType jdbcType;
        private SqlColumn<?> column;
        private Field readField;

        private SqlColumn<?> buildColumn() {
            return new SqlColumn.Builder()
                .withJdbcType(jdbcType)
                .withName(name)
                .withTable(TableInfoImpl.this.table())
                .build()
                .as(property());
        }

        @Override
        public String property() {
            return readField.getName();
        }

        @Override
        public Object readValue(Object entity) {
            try {
                return readField.get(entity);
            } catch (IllegalAccessException ignore) { }

            return null;
        }

        @Override
        public SqlColumn<?> sqlColumn() {
            if (column == null) {
                column = buildColumn();
            }
            return column;
        }
    }

    @Setter
    @Getter
    @Accessors(chain = true, fluent = true)
    @ToString
    class IdInfoImpl extends ColumnInfoImpl implements IdInfo {

        private String generatedType;
        private String generator;

    }

}

