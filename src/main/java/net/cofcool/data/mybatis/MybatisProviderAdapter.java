package net.cofcool.data.mybatis;

import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;

public class MybatisProviderAdapter {

    public <T> String insert(T entity) {
        return SqlBuilder
            .insert(entity)
            .into(MetadataHelper.getMetadataManager().getTable(entity.getClass()).table())
            .build()
            .render(RenderingStrategies.MYBATIS3)
            .getInsertStatement();
    }

}
