package net.cofcool.data.mybatis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.cofcool.data.mybatis.metadata.MetadataManager;
import net.cofcool.data.mybatis.metadata.TableInfo;

public class DefaultMetadataManager implements MetadataManager {

    public Map<Class<?>, TableInfo> tableInfoMap = new HashMap<>();
    private final MybatisConfiguration configuration;

    public DefaultMetadataManager(MybatisConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public TableInfo table(Class<?> tableType) {
        TableInfo tableInfo = tableInfoMap.get(tableType);
        if (tableInfo == null) {
            throw new TableNotFoundException(String.format("The class %s has not be parsed to a entity", tableType.getName()));
        }
        return tableInfo;
    }

    @Override
    public Set<TableInfo> allTables() {
        return new HashSet<>(tableInfoMap.values());
    }

    @Override
    public MybatisConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void parseTable(Class<?> tableType) {
        tableInfoMap.put(tableType, new TableInfoImpl(tableType, configuration.getNamingStrategy()));
    }
}
