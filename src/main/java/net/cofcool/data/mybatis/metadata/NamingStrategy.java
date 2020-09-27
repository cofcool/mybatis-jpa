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

/**
 *
 * @author CofCool
 */
public interface NamingStrategy {

    String physicalName(String name);

    String logicalName(String name);

    static NamingStrategy defaultStrategy(String prefix) {
        return new DefaultStrategy(prefix);
    }

    final class DefaultStrategy implements NamingStrategy {

        private final String prefix;

        public DefaultStrategy(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public String physicalName(String name) {
            String newPrefix = prefix == null ? "" : prefix + "_";

            StringBuilder builder = new StringBuilder(name);
            builder.replace(0, 1, name.substring(0, 1).toLowerCase());

            for (int i = 1; i < builder.length() - 1; i++) {
                char ch = builder.charAt(i);
                if (Character.isLowerCase(builder.charAt(i - 1)) && Character.isUpperCase(ch)
                    && Character.isLowerCase(builder.charAt(i + 1))) {
                    builder.replace(i, i + 1, String.valueOf(Character.toLowerCase(ch)));
                    builder.insert(i++, '_');
                }
            }
            return newPrefix + builder.toString();
        }

        @Override
        public String logicalName(String name) {
            StringBuilder builder = new StringBuilder(name);
            if (prefix != null) {
                builder.delete(0, prefix.length());
            }

            for (int i = 0; i < builder.length(); i++) {
                if (builder.charAt(i) == '_') {
                    builder.delete(i, i + 1);
                    builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
                }
            }

            return builder.toString();
        }

    }
}
