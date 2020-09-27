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

import org.apache.ibatis.exceptions.IbatisException;

/**
 *
 * @author CofCool
 */
public class TableNotFoundException extends IbatisException {

    private static final long serialVersionUID = 8882056849386478752L;

    public TableNotFoundException() {
    }

    public TableNotFoundException(String message) {
        super(message);
    }

    public TableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableNotFoundException(Throwable cause) {
        super(cause);
    }
}
