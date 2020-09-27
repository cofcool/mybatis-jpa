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
 * 未找到列
 * @author CofCool
 */
public class ColumnNotFoundException extends IbatisException {

    private static final long serialVersionUID = -176476585845021622L;

    public ColumnNotFoundException() {
    }

    public ColumnNotFoundException(String message) {
        super(message);
    }

    public ColumnNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColumnNotFoundException(Throwable cause) {
        super(cause);
    }
}
