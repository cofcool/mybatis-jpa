package net.cofcool.data.mybatis;

import org.apache.ibatis.exceptions.IbatisException;

/**
 * 未找到列
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
