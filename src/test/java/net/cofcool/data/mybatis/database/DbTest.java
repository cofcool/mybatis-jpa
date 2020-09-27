package net.cofcool.data.mybatis.database;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;
import javax.sql.DataSource;
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
        Assertions.assertTrue(userMapper.insert(new User(1L, "test", "test", LocalDateTime.now())));
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
