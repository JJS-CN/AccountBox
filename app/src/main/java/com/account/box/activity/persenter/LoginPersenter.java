package com.account.box.activity.persenter;

import android.text.TextUtils;

import com.account.box.activity.view.LoginView;
import com.account.box.bean.RxResult;
import com.account.box.bean.UserBean;
import com.account.box.http.ApiService;
import com.account.box.http.RxSub;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.http.RetrofitUtils;
import com.jjs.base.http.RxSchedulers;
import com.jjs.base.mvp.BasePersenter;

/**
 * 说明：
 * Created by aa on 2017/8/17.
 */

public class LoginPersenter extends BasePersenter<LoginView> {

    public LoginPersenter(LoginView view) {
        super(view);
    }

    public void sendSms(String phone) {
        RetrofitUtils.getInstance()
                .create(ApiService.Login.class)
                .sendSMS(phone)
                .compose(RxSchedulers.getInstance(mView.bindToLifecycle()).<RxResult<UserBean>>io_main())
                .subscribe(new RxSub<UserBean>() {
                    @Override
                    public void _onSuccess(UserBean body) {

                    }
                });
    }

    public void login(String phone, String pwd, String sms) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }



    }

    public void register(String phone, String pwd) {

    }

    public void resetPwd(String phone, String oldPwd, String newPwd, String newPwd2) {

    }
}
