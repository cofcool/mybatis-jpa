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

import net.cofcool.data.mybatis.metadata.MetadataManager;
import net.cofcool.data.mybatis.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 *
 * @author CofCool
 */
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