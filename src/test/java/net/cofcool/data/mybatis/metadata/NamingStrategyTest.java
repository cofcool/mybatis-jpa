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

package net.cofcool.data.mybatis.metadata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author CofCool
 */
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