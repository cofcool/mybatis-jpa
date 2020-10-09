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

import net.cofcool.data.mybatis.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 *
 * @author CofCool
 */
class MybatisProviderAdapterTest {

    @BeforeAll
    static void setup() {
        DefaultMetadataManager metadataManager = new DefaultMetadataManager(
            new MybatisConfiguration());
        metadataManager.parseTable(User.class);
        MetadataHelper.setMetadataManager(metadataManager);

    }

    @Test
    void insertTest() {
        System.out.println(
            new MybatisProviderAdapter()
                .insert(new User(1L, "test", "test", LocalDateTime.now()))
        );
    }

    @Test
    void queryTest() {
        System.out.println(
            new MybatisProviderAdapter()
                .query(new User(1L, "test", null, LocalDateTime.now()))
        );
    }

    @Test
    void deleteTest() {
        System.out.println(
            new MybatisProviderAdapter()
                .delete(new User(1L, "test", null, LocalDateTime.now()))
        );
    }

    @Test
    void deleteParamErrorTest() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> new MybatisProviderAdapter().delete(new User())
        );
    }
}