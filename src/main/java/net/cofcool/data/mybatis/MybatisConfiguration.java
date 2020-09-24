package net.cofcool.data.mybatis;

import java.util.HashMap;
import java.util.Map;
import net.cofcool.data.mybatis.metadata.IdGenerator;
import net.cofcool.data.mybatis.metadata.NamingStrategy;
import org.apache.ibatis.session.Configuration;


public class MybatisConfiguration extends Configuration {

    private NamingStrategy namingStrategy = NamingStrategy.defaultStrategy(null);

    private final Map<String, IdGenerator> generatorMap = new HashMap<>();


    public NamingStrategy getNamingStrategy() {
        return namingStrategy;
    }

    public void setNamingStrategy(NamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public void idRegister(String generator, IdGenerator idGenerator) {
        generatorMap.put(generator, idGenerator);
    }

    public IdGenerator getIdGenerator(String generator) {
        return generatorMap.get(generator);
    }
}
