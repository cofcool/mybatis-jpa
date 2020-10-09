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

package net.cofcool.data.mybatis.database;


import net.cofcool.data.mybatis.DefaultMetadataManager;
import net.cofcool.data.mybatis.MetadataHelper;
import net.cofcool.data.mybatis.MybatisConfiguration;
import net.cofcool.data.mybatis.model.User;
import net.cofcool.data.mybatis.model.UserMapper;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.Environment.Builder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author CofCool
 */
class DbTest {

    static SqlSessionFactory factory;

    @BeforeAll
    static void setup() throws IOException {
        Environment environment = new Builder("d")
            .dataSource(createUnpooledDataSource("h2.properties"))
            .transactionFactory(new JdbcTransactionFactory())
            .build();
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration(environment);
        factory = new SqlSessionFactoryBuilder().build(mybatisConfiguration);

        new ScriptRunner(factory.openSession().getConnection()).runScript(Resources.getResourceAsReader("h2-init.sql"));

        DefaultMetadataManager metadataManager = new DefaultMetadataManager(mybatisConfiguration);
        metadataManager.parseTable(User.class);
        MetadataHelper.setMetadataManager(metadataManager);
    }

    @Test
    void insertTest() {
        Configuration configuration = factory.getConfiguration();
        configuration.addMapper(UserMapper.class);
        UserMapper userMapper = configuration.getMapper(UserMapper.class, factory.openSession());
        for (int i = 0; i < 10; i++) {
            Assertions.assertTrue(userMapper.insert(new User((long) i, "test" + i, "test", LocalDateTime.now())));
        }
    }

    @Test
    void queryTest() {
        Configuration configuration = factory.getConfiguration();
        configuration.addMapper(UserMapper.class);
        UserMapper userMapper = configuration.getMapper(UserMapper.class, factory.openSession());
        for (int i = 0; i < 10; i++) {
            userMapper.insert(new User((long) i, "test" + i, "test", LocalDateTime.now()));
        }
        List<User> users = userMapper.query(new User(1L, null, null, null));
        Assertions.assertEquals(1, users.size());
        Assertions.assertNotNull(userMapper.query(new User(1L, "test", "test", LocalDateTime.now())));
    }

    @Test
    void deleteTest() {
        Configuration configuration = factory.getConfiguration();
        configuration.addMapper(UserMapper.class);
        UserMapper userMapper = configuration.getMapper(UserMapper.class, factory.openSession());
        long id = 10;
        userMapper.insert(new User(id, "test", "test", LocalDateTime.now()));
        Assertions.assertTrue(userMapper.delete(new User(id, null, null, null)));
    }


    public static DataSource createUnpooledDataSource(String resource) throws IOException {
        Properties props = Resources.getResourceAsProperties(resource);
        UnpooledDataSource ds = new UnpooledDataSource();
        ds.setDriver(props.getProperty("driver"));
        ds.setUrl(props.getProperty("url"));
        ds.setUsername(props.getProperty("username"));
        ds.setPassword(props.getProperty("password"));
        return ds;
    }
}
