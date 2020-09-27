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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.cofcool.data.mybatis.metadata.MetadataManager;
import net.cofcool.data.mybatis.metadata.TableInfo;

/**
 *
 * @author CofCool
 */
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
