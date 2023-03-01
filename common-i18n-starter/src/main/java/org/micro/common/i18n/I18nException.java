package org.micro.common.i18n;


import org.micro.commons.basic.beans.Result;
import org.micro.commons.basic.exception.BasicException;

/**
 * 国际化Message统一抛出异常处理
 */
public class I18nException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Result result;

    private I18nModel i18nModel;

    public I18nException() {
        super();
    }

    public I18nException(Result result) {
        super();
        this.result = result;
    }

    public I18nException(Throwable cause) {
        super(cause);
        this.result = new Result(Result.ResultCode.FAIL, BasicException.getRootCauseMsg(cause));
    }

    public I18nException(int errcode, I18nModel i18nModel) {
        this.i18nModel = i18nModel;
        this.result = new Result(i18nModel);
        this.result.setCode(errcode);
    }

    public I18nException(String message, Throwable cause) {
        super(message, cause);
        this.result = new Result(Result.ResultCode.FAIL, message);
    }

    public I18nException(String i18nCode, Object... i18nMsgArgs) {
        this(Result.ResultCode.FAIL, new I18nModel(i18nCode, i18nMsgArgs));
    }

    public I18nException(int errcode, String i18nCode, Object... i18nMsgArgs) {
        this(errcode, new I18nModel(i18nCode, i18nMsgArgs));
    }

    public Result getResult() {
        return result;
    }

    public I18nModel getI18nModel() {
        return i18nModel;
    }
}
