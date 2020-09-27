package net.cofcool.data.mybatis;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.cofcool.data.mybatis.metadata.TableInfo;
import net.cofcool.data.mybatis.metadata.TableInfo.ColumnInfo;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.insert.InsertDSL;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;

public class MybatisProviderAdapter implements ProviderMethodResolver {

    @Override
    public Method resolveMethod(ProviderContext context) {
        for (Type genericInterface : context.getMapperType().getGenericInterfaces()) {
            if (genericInterface.getTypeName().equals(CrudMapper.class.getTypeName())) {
                Type[] actualTypeArguments = ((ParameterizedType) genericInterface)
                    .getActualTypeArguments();
                Type entityType = actualTypeArguments[0];
                Type idType = actualTypeArguments[1];
            }
        }

        List<Method> sameNameMethods = Arrays.stream(getClass().getMethods())
            .filter(m -> m.getName().equals(context.getMapperMethod().getName()))
            .collect(Collectors.toList());
        if (sameNameMethods.isEmpty()) {
            throw new BuilderException("Cannot resolve the provider method because '"
                + context.getMapperMethod().getName() + "' not found in SqlProvider '" + getClass().getName() + "'.");
        }
        List<Method> targetMethods = sameNameMethods.stream()
            .filter(m -> CharSequence.class.isAssignableFrom(m.getReturnType()))
            .collect(Collectors.toList());
        if (targetMethods.size() == 1) {
            return targetMethods.get(0);
        }
        if (targetMethods.isEmpty()) {
            throw new BuilderException("Cannot resolve the provider method because '"
                + context.getMapperMethod().getName() + "' does not return the CharSequence or its subclass in SqlProvider '"
                + getClass().getName() + "'.");
        } else {
            throw new BuilderException("Cannot resolve the provider method because '"
                + context.getMapperMethod().getName() + "' is found multiple in SqlProvider '" + getClass().getName() + "'.");
        }
    }

    public <T> String insert(T entity) {
        if (entity instanceof ParamMap) {
            entity = (T) ((ParamMap) entity).get("record");
        }
        TableInfo tableInfo = getTable(entity);
        InsertDSL<T> dsl = SqlBuilder
            .insert(entity)
            .into(tableInfo.table());
        for (ColumnInfo columnInfo : tableInfo.allColumns()) {
            dsl.map(columnInfo.sqlColumn()).toProperty(columnInfo.property());
        }
        return dsl
            .build()
            .render(RenderingStrategies.MYBATIS3)
            .getInsertStatement();
    }

    public static <T> TableInfo getTable(T entity) {
        return MetadataHelper.getMetadataManager().table(entity.getClass());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> String select(T entity) {
        TableInfo tableInfo = getTable(entity);
        Set<ColumnInfo> columnInfos = tableInfo.allColumns();
        SqlColumn[] columns = columnInfos.stream()
            .map(ColumnInfo::sqlColumn)
            .toArray(SqlColumn[]::new);
        QueryExpressionDSL<SelectModel> dsl = SqlBuilder
            .select(columns)
            .from(tableInfo.table());
        columnInfos.forEach(c -> {
            SqlColumn<T> column = (SqlColumn<T>)c.sqlColumn();
            dsl.where(column, SqlBuilder.isEqualToWhenPresent(c.readValue(entity)));
        });

        return dsl.build().render(RenderingStrategies.MYBATIS3).getSelectStatement();
    }

}
