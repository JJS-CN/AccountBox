package com.account.box.activity.persenter;

import android.text.TextUtils;

import com.account.box.APP;
import com.account.box.Store;
import com.account.box.activity.view.LoginView;
import com.account.box.bean.RxResult;
import com.account.box.bean.UserBean;
import com.account.box.http.ApiService;
import com.account.box.http.RxObserver;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.http.RetrofitUtils;
import com.jjs.base.http.RxSchedulers;
import com.jjs.base.mvp.BasePersenter;

import java.lang.reflect.Field;

/**
 * 说明：
 * Created by aa on 2017/8/17.
 */

public class LoginPersenter extends BasePersenter<LoginView> {


    public LoginPersenter(LoginView view) {
        super(view);
    }

    public void sendSms(String phone) {
    }

    public void login(final String name, String pwd, String imei) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        RetrofitUtils.getInstance().create(ApiService.Login.class)
                .login(name, pwd, imei)
                .compose(RxSchedulers.getInstance(mView.bindToLifecycle()).<RxResult<UserBean>>io_main())
                .subscribe(new RxObserver<UserBean>() {
                    @Override
                    protected void _onSuccess(UserBean userBean) {
                        SPUtils.getInstance().put("username", name);
                        if (!TextUtils.isEmpty(userBean.getUser().getHead_path())) {
                            SPUtils.getInstance().put(userBean.getUser().getAccount() + "avatar", userBean.getUser().getHead_path());
                        }
                        APP.getInstance().mUserBean = userBean;
                        mView.userBeanSuccess();
                    }
                });
    }

    public void register(final String account, String pwd) {
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        RetrofitUtils.getInstance().create(ApiService.Login.class).register(account, pwd)
                .compose(RxSchedulers.getInstance(mView.bindToLifecycle()).<RxResult<String>>io_main())
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void _onSuccess(String String) {
                        SPUtils.getInstance().put("username", account);
                        mView.userBeanSuccess();
                    }
                });
    }

    public void resetPwd(String phone, String oldPwd, String newPwd, String newPwd2, int pwdType) {
        Class aClass = Store.Password.class;
        Field[] fields = aClass.getDeclaredFields();
        boolean isType = false;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getType().getName().equals("int")) {
                try {
                    if (fields[i].getInt(aClass) == pwdType) {
                        isType = true;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!isType) {
            ToastUtils.showShort("密码类型不能为空！！！");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.showShort("旧密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.showShort("新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(newPwd2)) {
            ToastUtils.showShort("确认新密码不能为空");
            return;
        }
        if (newPwd.equals(newPwd2)) {
            ToastUtils.showShort("2次新密码不相同");
            return;
        }
    }
}
