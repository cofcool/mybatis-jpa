package net.cofcool.data.mybatis.metadata;

import java.util.Set;
import net.cofcool.data.mybatis.MybatisConfiguration;

public interface MetadataManager {

    TableInfo table(Class<?> tableType);

    Set<TableInfo> allTables();

    MybatisConfiguration getConfiguration();

    void parseTable(Class<?> tableType);

}
