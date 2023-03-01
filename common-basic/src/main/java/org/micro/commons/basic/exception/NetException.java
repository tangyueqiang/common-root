package org.micro.commons.basic.exception;

/**
 * 网络异常
 */
public class NetException extends BasicException {

    private static final long serialVersionUID = 1L;

    public NetException() {
        super();
    }

    public NetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetException(String message) {
        super(message);
    }

    public NetException(Throwable cause) {
        super(cause);
    }

}
