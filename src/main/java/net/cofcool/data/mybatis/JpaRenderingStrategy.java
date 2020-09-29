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

import org.mybatis.dynamic.sql.BindableColumn;
import org.mybatis.dynamic.sql.Constant;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.render.RenderingStrategy;

/**
 * 忽略前缀，如 {@code #{userId}}, 而 {@link org.mybatis.dynamic.sql.render.RenderingStrategies#MYBATIS3} 会添加前缀 {@code #{record.userId}}
 *
 * @author CofCool
 */
public class JpaRenderingStrategy extends RenderingStrategy {

    private final MybatisConfiguration mybatisConfiguration;

    public JpaRenderingStrategy(MybatisConfiguration mybatisConfiguration) {
        this.mybatisConfiguration = mybatisConfiguration;
    }

    @Override
    public String getFormattedJdbcPlaceholder(String prefix, String parameterName) {
        return "#{"
            + parameterName
            + "}";
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String getFormattedJdbcPlaceholder(BindableColumn<?> column, String prefix, String parameterName) {
        if (column instanceof SqlColumn) {
            parameterName = mybatisConfiguration.getNamingStrategy().logicalName(((SqlColumn)column).name());
        } else if (column instanceof Constant) {
            return column.renderWithTableAlias(null);
        }

        return "#{"
            + parameterName
            + renderJdbcType(column)
            + renderTypeHandler(column)
            + "}";
    }

    private String renderTypeHandler(BindableColumn<?> column) {
        return column.typeHandler()
            .map(th -> ",typeHandler=" + th) //$NON-NLS-1$
            .orElse(""); //$NON-NLS-1$
    }

    private String renderJdbcType(BindableColumn<?> column) {
        return column.jdbcType()
            .map(jt -> ",jdbcType=" + jt.getName()) //$NON-NLS-1$
            .orElse(""); //$NON-NLS-1$
    }
}
