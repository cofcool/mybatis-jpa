package net.cofcool.data.mybatis.metadata;

import java.util.Set;
import javax.security.auth.login.Configuration;
import net.cofcool.data.mybatis.MybatisConfiguration;

public interface MetadataManager {

    TableInfo getTable(Class<?> tableType);

    Set<TableInfo> getAllTables();

    MybatisConfiguration getConfiguration();

    void setConfiguration(MybatisConfiguration configuration);

}
