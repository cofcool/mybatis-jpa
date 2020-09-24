package net.cofcool.data.mybatis;

import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.cofcool.data.mybatis.metadata.IdGenerator;
import net.cofcool.data.mybatis.metadata.NamingStrategy;
import net.cofcool.data.mybatis.metadata.TableInfo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.mybatis.dynamic.sql.SqlTable;

public class TableInfoImpl implements TableInfo {

    private final Class<?> type;
    private String tableName;
    private SqlTable sqlTable;
    private final NamingStrategy namingStrategy;

    private Map<String, ColumnInfo> columnInfos = new HashMap();


    public TableInfoImpl(Class<?> type, NamingStrategy namingStrategy) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(namingStrategy);
        this.type = type;
        this.namingStrategy = namingStrategy;
    }

    public ColumnInfo getColumn(String columnName) {
        return null;
    }

    @Override
    public Class<?> getJavaType() {
        return type;
    }

    @Override
    public Set<ColumnInfo> getAllColumns() {
        return new HashSet<>(columnInfos.values());
    }

    @Override
    public SqlTable getTable() {
        return sqlTable;
    }

    @Override
    public String getName() {
        return tableName;
    }

    @Override
    public IdInfo getId() {
        return null;
    }

    private void parsingEntity() {
        Entity entity = type.getAnnotation(Entity.class);
        Table table = type.getAnnotation(Table.class);
        String tableName = table.name();
        if (tableName.isEmpty()) {
            tableName = namingStrategy.physicalName(null, type.getSimpleName());
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
        for (Field field : type.getFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(Transient.class) != null) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);

            Id id = field.getAnnotation(Id.class);
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);

            ColumnInfo columnInfo;
            if (id != null) {
                columnInfo = new IdInfoImpl()
                    .setId(true)
                    .setJavaType(field.getType())
                    .setName(field.getName());
                hasId = true;
            } else {
                columnInfo = new ColumnInfoImpl()
                    .setJavaType(field.getType())
                    .setName(field.getName());
            }
            columnInfos.put(columnInfo.getName(), columnInfo);
        }

        if (!hasId) {
            throw new IllegalStateException("The entity must have primary key");
        }
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    static class ColumnInfoImpl implements ColumnInfo {

        private boolean id;
        private String name;
        private String alias;
        private Class<?> javaType;
        private JDBCType JDBCType;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    static class IdInfoImpl extends ColumnInfoImpl implements IdInfo {

        private String generatedType;
        private IdGenerator generator;

    }

}

