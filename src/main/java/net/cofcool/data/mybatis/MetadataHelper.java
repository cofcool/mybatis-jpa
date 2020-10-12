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

import java.util.Objects;
import net.cofcool.data.mybatis.metadata.MetadataManager;
import net.cofcool.data.mybatis.metadata.TableInfo;

/**
 * 元数据相关工具类, 如 {@link MetadataManager}, {@link TableInfo} 等
 * @author CofCool
 */
public abstract class MetadataHelper {

    private static MetadataManager metadataManager;

    MetadataHelper() {
    }

    /**
     * 获取 {@link MetadataManager} 实例
     * @return metadataManager 实例
     * @throws NullPointerException MetadataHelper 未持有 metadataManager
     */
    public static MetadataManager getMetadataManager() {
        Objects.requireNonNull(metadataManager, "metadataManager must be specified");
        return metadataManager;
    }

    /**
     * 配置 {@link MetadataManager}
     * @param metadataManager metadataManager 实例
     * @throws IllegalStateException MetadataHelper 已持有 metadataManager
     */
    public static void setMetadataManager(MetadataManager metadataManager) {
        if (MetadataHelper.metadataManager != null) {
            throw new IllegalStateException("MetadataHelper already held metadataManager");
        }
        MetadataHelper.metadataManager = metadataManager;
    }

    /**
     * 根据实体获取表元数据
     * @param entity 实体
     * @param <T> 实体类型
     * @return 表元数据
     */
    public static <T> TableInfo tableInfo(T entity) {
        return metadataManager.table(entity.getClass());
    }

    /**
     * 根据实体类型获取表元数据
     * @param entityType 实体类型
     * @param <T> 实体类型
     * @return 表元数据
     */
    public static <T> TableInfo tableInfo(Class<T> entityType) {
        return metadataManager.table(entityType);
    }

}
