package net.cofcool.data.mybatis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import net.cofcool.data.mybatis.metadata.NamingStrategy;
import net.cofcool.data.mybatis.metadata.TableInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TableInfoImplTest {

    static TableInfo tableInfo;

    @BeforeAll
    static void setupTable() {
        tableInfo = new TableInfoImpl(User.class, NamingStrategy.defaultStrategy(null));
    }

    @Test
    void columnTest() {
        assertEquals("username",  tableInfo.column("username").name());
        assertEquals(String.class,  tableInfo.column("username").javaType());
        assertEquals("sign_up_time",  tableInfo.column("sign_up_time").name());
        assertThrows(ColumnNotFoundException.class, () -> tableInfo.column("test11"));

    }

    @Test
    void javaTypeTest() {
    }

    @Test
    void allColumnsTest() {
    }

    @Test
    void tableTest() {
        assertEquals("user", tableInfo.table().tableNameAtRuntime());
    }

    @Test
    void nameTest() {
        assertNotNull(tableInfo.name());
    }

    @Test
    void idTest() {
        assertNotNull(tableInfo.id());
    }

    @Entity
    @Table(name = "user")
    @Getter
    @Setter
    public static class User {

        @Id
        private Long userId;
        private String username;
        private String desc;
        private LocalDateTime signUpTime;

    }
}