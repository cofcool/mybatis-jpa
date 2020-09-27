package net.cofcool.data.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import net.cofcool.data.mybatis.metadata.MetadataManager;
import net.cofcool.data.mybatis.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DefaultMetadataManagerTest {

    private static MetadataManager metadataManager;

    @BeforeAll
    static void setup() {
        metadataManager = new DefaultMetadataManager(new MybatisConfiguration());
        metadataManager.parseTable(User.class);
    }

    @Test
    void getTable() {
        assertNotNull(metadataManager.table(User.class));
        assertThrows(TableNotFoundException.class, () -> metadataManager.table(NullPointerException.class));
    }

    @Test
    void getAllTables() {
        assertEquals(1, metadataManager.allTables().size());
    }


}