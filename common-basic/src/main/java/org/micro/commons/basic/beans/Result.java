package org.micro.commons.basic.beans;

import com.alibaba.fastjson.JSONObject;


import static org.micro.commons.basic.beans.Result.ResultCode.FAIL;

public class Result<T> {

    private int code = ResultCode.SUCESS;
    private String msg = "";
    private T model = null;

    public Result() {
    }

    public Result(T model) {
        this.model = model;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T model) {
        this.code = code;
        this.msg = msg;
        this.model = model;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    /**
     * 状态码
     */
    public static class ResultCode {

        public static final int SUCESS = 0;
        public static final int FAIL = 1;
    }

    public static <T> Result<T> success() {
        return new Result<>();
    }

    public static <T> Result<T> success(T model) {
        return new Result<>(model);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(FAIL, msg);
    }
}
