package org.micro.commons.basic.exception;

/**
 * 不支持的异常
 */
public class NotSupportedException extends BasicException {

    private static final long serialVersionUID = 1L;

    public NotSupportedException() {
        super();
    }

    public NotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException(Throwable cause) {
        super(cause);
    }

}
