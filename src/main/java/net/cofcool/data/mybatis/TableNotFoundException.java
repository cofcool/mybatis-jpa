package net.cofcool.data.mybatis;

import org.apache.ibatis.exceptions.IbatisException;

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
