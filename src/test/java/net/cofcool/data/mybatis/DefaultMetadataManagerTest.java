package net.cofcool.data.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import net.cofcool.data.mybatis.TableInfoImplTest.User;
import net.cofcool.data.mybatis.metadata.MetadataManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DefaultMetadataManagerTest {

    private static MetadataManager metadataManager;

    @BeforeAll
    static void setup() {
        metadataManager = new DefaultMetadataManager();
        metadataManager.setConfiguration(new MybatisConfiguration());
        metadataManager.parseTable(User.class);
    }

    @Test
    void getTable() {
        assertNotNull(metadataManager.getTable(User.class));
        assertThrows(TableNotFoundException.class, () -> metadataManager.getTable(NullPointerException.class));
    }

    @Test
    void getAllTables() {
        assertEquals(1, metadataManager.getAllTables().size());
    }


}