package net.cofcool.data.mybatis.metadata;


public interface NamingStrategy {

    String physicalName(String prefix, String name);

    String logicalName(String prefix, String name);

    static NamingStrategy defaultStrategy() {
        return new DefaultStrategy();
    }

    final class DefaultStrategy implements NamingStrategy {

        @Override
        public String physicalName(String prefix, String name) {
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
        public String logicalName(String prefix, String name) {
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
