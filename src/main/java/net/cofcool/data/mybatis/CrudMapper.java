package net.cofcool.data.mybatis;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrudMapper<T, ID> {

    @InsertProvider(value = MybatisProviderAdapter.class, method = "insert")
    T insert(T entity);

}
