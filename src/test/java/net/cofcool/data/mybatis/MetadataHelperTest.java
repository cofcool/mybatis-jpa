package net.cofcool.data.mybatis;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MetadataHelperTest {

    @BeforeAll
    static void setup() {
        MetadataHelper.setMetadataManager(new DefaultMetadataManager(new MybatisConfiguration()));
    }

    @Test
    void getMetadataManager() {
        assertNotNull(MetadataHelper.getMetadataManager());
    }

    @Test
    void setMetadataManager() {
        assertThrows(IllegalStateException.class, MetadataHelperTest::setup);
    }
}