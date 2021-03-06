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
import java.util.Map;
import net.cofcool.data.mybatis.metadata.IdGenerator;
import net.cofcool.data.mybatis.metadata.NamingStrategy;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;


/**
 *
 * @author CofCool
 */
public class MybatisConfiguration extends Configuration {

    public MybatisConfiguration(Environment environment) {
        super(environment);
    }

    public MybatisConfiguration() {
    }

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
