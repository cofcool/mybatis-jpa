package net.cofcool.data.mybatis;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.cofcool.data.mybatis.metadata.NamingStrategy;
import org.apache.ibatis.session.Configuration;

@Getter
@Setter
@Accessors(chain = true)
public class MybatisConfiguration extends Configuration {

    private NamingStrategy namingStrategy = NamingStrategy.defaultStrategy();



}
