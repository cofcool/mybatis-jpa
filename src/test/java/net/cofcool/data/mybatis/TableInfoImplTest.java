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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import net.cofcool.data.mybatis.metadata.NamingStrategy;
import net.cofcool.data.mybatis.metadata.TableInfo;
import net.cofcool.data.mybatis.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author CofCool
 */
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
}