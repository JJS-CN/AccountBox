package com.account.box.http;

import com.account.box.bean.RxResult;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.http.RxSubject;
import com.jjs.base.widget.LoadingDialog;

/**
 * 说明：
 * Created by aa on 2017/8/15.
 */

public abstract class RxSub<T> extends RxSubject<RxResult<T>> {

    @Override
    protected void _onNext(RxResult<T> tRxResult) {
        //根据新建立的模型，进行请求是否成功的判断
        if (tRxResult.isType()) {
            _onSuccess(tRxResult.getBody());
        } else {
            _onError(tRxResult.getMsg());
        }

    }

    @Override
    protected void _onComplete() {
        LoadingDialog.hide();//新版本已在父类实现
    }

    @Override
    protected void _onError(String s) {
        //判断是否展示toast，在实现时复写方法showToast进行控制
        if (showToast())
            ToastUtils.showShort(s);
    }

    public abstract void _onSuccess(T body);

}
