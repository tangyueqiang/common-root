package org.micro.commons.basic.exception;

/**
 * 连接过多异常
 */
public class TooManyConnectionException extends BasicException {

    private static final long serialVersionUID = 1L;

    public TooManyConnectionException() {
        super();
    }

    public TooManyConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyConnectionException(String message) {
        super(message);
    }

    public TooManyConnectionException(Throwable cause) {
        super(cause);
    }

}
