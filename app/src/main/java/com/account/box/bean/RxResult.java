package com.account.box.bean;

import java.io.Serializable;

/**
 * 说明：
 * Created by aa on 2017/8/15.
 */

public class RxResult<T> implements Serializable {
    private boolean type;//标识接口是否请求成功
    private int code;//错误码
    private String msg;//错误提示
    T body;//正确时的json数据

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
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

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RxResult{" +
                "type=" + type +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", body=" + body +
                '}';
    }
}
