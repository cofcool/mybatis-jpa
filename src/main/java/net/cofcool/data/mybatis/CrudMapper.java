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

import java.io.Serializable;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * CRUD 基类, 定义基本方法
 *
 * @param <T> 实体类型
 * @param <ID> ID
 * @author CofCool
 */
@Mapper
public interface CrudMapper<T, ID extends Serializable> {

    /**
     * 插入
     * @param entity 实体
     * @return 是否成功
     */
    @InsertProvider(value = MybatisProviderAdapter.class, method = "insert")
    boolean insert(T entity);

    /**
     * 查询, 不为 {@code null} 的属性作为查询条件
     * @param entity 实体
     * @return 查询数据集
     */
    @SelectProvider(value = MybatisProviderAdapter.class, method = "query")
    List<T> query(T entity);

    /**
     * 删除
     * @param entity 实体
     * @return 是否成功
     */
    @DeleteProvider(value = MybatisProviderAdapter.class, method = "delete")
    boolean delete(T entity);

}
