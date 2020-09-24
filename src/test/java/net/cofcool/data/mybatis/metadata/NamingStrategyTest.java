package net.cofcool.data.mybatis.metadata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NamingStrategyTest {

    public static final String NAME = "UserDetailInfo";
    public static final String TABLE_NAME = "u_user_detail_info";

    @Test
    void physicNameTest() {
        Assertions.assertEquals(TABLE_NAME, NamingStrategy.defaultStrategy("u").physicalName(NAME));
    }

    @Test
    void logicNameTest() {
        Assertions.assertEquals(NAME, NamingStrategy.defaultStrategy("u").logicalName(TABLE_NAME));
    }
}