package org.micro.commons.basic.exception;

/**
 * 自定义FeignClient调用异常
 */
public class FeignClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code = 500;

    public FeignClientException() {
        super();
    }

    public FeignClientException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public FeignClientException(int code, String message) {
        super(message);
        this.code = code;
    }

    public FeignClientException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
