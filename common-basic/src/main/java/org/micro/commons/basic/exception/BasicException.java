package org.micro.commons.basic.exception;

import org.apache.commons.lang.StringUtils;
import org.micro.commons.basic.beans.Result.ResultCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 统一异常类
 */
public class BasicException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 错误代码
     */
    private int errorcode = ResultCode.FAIL;

    /**
     * Constructs a new throwable with null as its detail message.
     */
    public BasicException() {
        super();
    }

    /**
     * Constructs a new throwable with the specified detail message.
     *
     * @param message - the detail message. The detail message is saved for later retrieval by the getMessage() method.
     */
    public BasicException(String message) {
        super(message);
    }

    public BasicException(int errcode, String message) {
        super(message);
        this.errorcode = errcode;
    }

    /**
     * Constructs a new throwable with the specified cause and a detail message of (cause==null ? null : cause.toString())
     * (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and
     *              indicates that the cause is nonexistent or unknown.)
     */
    public BasicException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new throwable with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and
     *                indicates that the cause is nonexistent or unknown.)
     */
    public BasicException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 带参构造器
     *
     * @param message 可带参数, 参数占位符"%s", String.format(msg, args)实现
     * @param cause   异常
     * @param msgArgs 参数列表
     */
    public BasicException(Throwable cause, String message, Object... msgArgs) {
        super(msgArgs != null ? String.format(message, msgArgs) : message.replace("%s", ""), cause);
    }

    /**
     * 获取异常原因
     *
     * @param ex 异常
     * @return 异常原因, 无字符串返回空字符串
     */
    public static String getCauseMsg(Throwable ex) {
        String error = ex.getMessage();
        if (ex instanceof BasicException) {
            BasicException de = (BasicException) ex;
            error = de.getSuperMessage();
        } else if (ex.getCause() != null) {
            error = ex.getCause().getMessage();
        }
        error = error == null ? "" : error;
        return error;
    }

    /***
     * 获取最终错误原因
     * @param ex 异常
     * @return 信息
     */
    public static String getRootCauseMsg(Throwable ex) {
        if (ex == null) {
            return "";
        }
        if (ex.getCause() == null) {
            return ex.getMessage();
        } else {
            return getRootCauseMsg(ex.getCause());
        }
    }

    public static BasicException throwBasicException(String err, Throwable ex) {
        BasicException de;
        if (ex instanceof BasicException) {
            de = (BasicException) ex;
        } else {
            de = new BasicException(err, ex);
        }
        return de;
    }

    /**
     * get the messages back to it's origin cause.
     */
    @Override
    public String getMessage() {
        String cr = System.getProperty("line.separator");
        StringBuilder retval = new StringBuilder();
        retval.append(super.getMessage()).append(cr);

        Throwable cause = getCause();
        if (cause != null) {
            String message = cause.getMessage();
            if (message != null) {
                retval.append(message).append(cr);
            } else {
                // Add with stack trace elements of cause...
                StackTraceElement[] ste = cause.getStackTrace();
                for (int i = ste.length - 1; i >= 0; i--) {
                    retval.append(" at ").append(ste[i].getClassName()).append(".").append(ste[i].getMethodName()).append(" (").append(ste[i].getFileName()).append(":").append(ste[i].getLineNumber()).append(")").append(cr);
                }
            }
        }

        return StringUtils.removeEnd(retval.toString(), cr);
    }

    /**
     * 异常信息
     *
     * @return String
     */
    public String getSuperMessage() {

        String err = getMessage() == null ? "" : getMessage();
        String cause = getCause() == null ? "" : getCause().getMessage();
        cause = StringUtils.isBlank(cause) ? "" : "Caused by:\n" + cause;

        List<String> rows = new ArrayList<>();
        rows.addAll(Arrays.asList(err.split("\n")));
        rows.addAll(Arrays.asList(cause.split("\n")));
        rows.removeIf(StringUtils::isBlank);

        return StringUtils.join(rows, "\n");
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

}
