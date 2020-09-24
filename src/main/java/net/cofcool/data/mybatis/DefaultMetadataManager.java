package net.cofcool.data.mybatis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.cofcool.data.mybatis.metadata.TableInfo;
import net.cofcool.data.mybatis.metadata.MetadataManager;
import net.cofcool.data.mybatis.metadata.NamingStrategy;

public class DefaultMetadataManager implements MetadataManager {

    public Map<Class<?>, TableInfo> tableInfoMap = new HashMap<>();

    @Override
    public TableInfo getTable(Class<?> tableType) {
        return null;
    }

    @Override
    public Set<TableInfo> getAllTables() {
        return null;
    }

    @Override
    public MybatisConfiguration getConfiguration() {
        return null;
    }

    @Override
    public void setConfiguration(MybatisConfiguration configuration) {

    }
}
