package org.micro.commons.basic.exception;

/**
 * 过期异常
 */
public class ExpiredException extends BasicException {

    private static final long serialVersionUID = 1L;

    public ExpiredException() {
        super();
    }

    public ExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredException(String message) {
        super(message);
    }

    public ExpiredException(Throwable cause) {
        super(cause);
    }

}
