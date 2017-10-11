package com.account.box.bean;

import java.io.Serializable;

/**
 * 说明：
 * Created by aa on 2017/8/15.
 */

public class RxResult<T> implements Serializable {
    private int error;//错误码
    private String msg;//错误提示
    T data;//正确时的json数据


    public boolean isSuccess() {
        if (error == 0)
            return true;
        else
            return false;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RxResult{" +
                "success=" + isSuccess() +
                ", error=" + error +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
