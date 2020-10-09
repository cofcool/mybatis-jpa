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

import net.cofcool.data.mybatis.metadata.TableInfo;
import net.cofcool.data.mybatis.metadata.TableInfo.ColumnInfo;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.DeleteModel;
import org.mybatis.dynamic.sql.insert.InsertDSL;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author CofCool
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MybatisProviderAdapter implements ProviderMethodResolver {

    private static final RenderingStrategy JPA_RENDERING_STRATEGY = new JpaRenderingStrategy(
        MetadataHelper.getMetadataManager().getConfiguration()
    );

    public <T> String insert(T entity) {
        TableInfo tableInfo = getTable(entity);
        InsertDSL<T> dsl = SqlBuilder
            .insert(entity)
            .into(tableInfo.table());
        for (ColumnInfo columnInfo : tableInfo.allColumns()) {
            dsl.map(columnInfo.sqlColumn()).toProperty(columnInfo.property());
        }
        return dsl
            .build()
            .render(JPA_RENDERING_STRATEGY)
            .getInsertStatement();
    }

    public static <T> TableInfo getTable(T entity) {
        return MetadataHelper.getMetadataManager().table(entity.getClass());
    }

    public <T> String query(T entity) {
        if (entity instanceof ParamMap) {
            entity = (T) ((ParamMap) entity).get("parameters");
        }
        TableInfo tableInfo = getTable(entity);
        Set<ColumnInfo> columnInfos = tableInfo.allColumns();
        SqlColumn[] columns = columnInfos.stream()
            .map(ColumnInfo::sqlColumn)
            .toArray(SqlColumn[]::new);
        QueryExpressionDSL<SelectModel> dsl = SqlBuilder
            .select(columns)
            .from(tableInfo.table());

        dsl.where(SqlBuilder.constant("1"), SqlBuilder.isEqualTo(1));
        for (ColumnInfo c : columnInfos) {
            SqlColumn column = c.sqlColumn();
            dsl.where().and(column, SqlBuilder.isEqualToWhenPresent(c.readValue(entity)));
        }

        return dsl.build().render(JPA_RENDERING_STRATEGY).getSelectStatement();
    }

    public <T> String delete(T entity) {
        TableInfo tableInfo = getTable(entity);
        Object val = tableInfo.id().readValue(entity);
        Objects.requireNonNull(val, "The entity id is must be specified");

        DeleteDSL<DeleteModel> dsl = SqlBuilder.deleteFrom(tableInfo.table());
        SqlColumn column = tableInfo.id().sqlColumn();
        dsl.where(column, SqlBuilder.isEqualTo(val));

        return dsl.build().render(JPA_RENDERING_STRATEGY).getDeleteStatement();
    }


}
