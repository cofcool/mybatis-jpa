package net.cofcool.data.mybatis;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CrudMapper<T, ID> {

    @InsertProvider(value = MybatisProviderAdapter.class)
    boolean insert(@Param("record") T entity);
}
