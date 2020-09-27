package net.cofcool.data.mybatis;

import java.time.LocalDateTime;
import net.cofcool.data.mybatis.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MybatisProviderAdapterTest {

    @BeforeAll
    static void setup() {
        DefaultMetadataManager metadataManager = new DefaultMetadataManager(
            new MybatisConfiguration());
        metadataManager.parseTable(User.class);
        MetadataHelper.setMetadataManager(metadataManager);

    }

    @Test
    void insert() {
        System.out.println(
            new MybatisProviderAdapter()
                .insert(new User(1L, "test", "test", LocalDateTime.now()))
        );
    }

    @Test
    void select() {
        System.out.println(
            new MybatisProviderAdapter()
                .select(new User(1L, "test", null, LocalDateTime.now()))
        );
    }
}