package com.account.box.http;

import com.account.box.bean.RxResult;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.http.JJsObserver;

/**
 * 说明：
 * Created by aa on 2017/10/9.
 */

public abstract class RxObserver<T> extends JJsObserver<RxResult<T>> {
    @Override
    protected void _onNext(RxResult<T> data) {
        if (data.isSuccess()) {
            _onSuccess(data.getData());
        } else {
            _onError(data.getMsg());
        }
    }

    @Override
    protected void _onComplete() {

    }

    @Override
    protected void _onError(String msg) {
        //失败调用,判断是否展示失败toast
        if (showToast())
            ToastUtils.showShort(msg);
    }

    //请求成功后调用
    protected abstract void _onSuccess(T t);
}
