package com.account.box.activity.persenter;

import android.text.TextUtils;

import com.account.box.APP;
import com.account.box.activity.view.LoginView;
import com.account.box.bean.RxResult;
import com.account.box.bean.UserBean;
import com.account.box.bean.UserBeanDao;
import com.account.box.bean.UserSQLDao;
import com.account.box.http.ApiService;
import com.account.box.http.RxSub;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.http.RetrofitUtils;
import com.jjs.base.http.RxSchedulers;
import com.jjs.base.mvp.BasePersenter;

import java.util.List;

/**
 * 说明：
 * Created by aa on 2017/8/17.
 */

public class LoginPersenter extends BasePersenter<LoginView> {
    UserBeanDao mUserBeanDao;

    public LoginPersenter(LoginView view) {
        super(view);
        mUserBeanDao = APP.getInstance().getDaoSession().getUserBeanDao();
    }

    public void sendSms(String phone) {
        RetrofitUtils.getInstance()
                .create(ApiService.Login.class)
                .sendSMS(phone)
                .compose(RxSchedulers.getInstance(mView.bindToLifecycle()).<RxResult<String>>io_main())
                .subscribe(new RxSub<String>() {
                    @Override
                    public void _onSuccess(String body) {
                        mView.sendSmsSuccess(body);
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
        List<UserBean> list = mUserBeanDao.queryBuilder()
                .where(UserSQLDao.Properties.Username.eq(phone))
                .build().list();
        if (list == null || list.size() == 0) {
            ToastUtils.showShort("用户不存在");
            return;
        }
        if (!list.get(0).getPassword().equals(pwd)) {
            ToastUtils.showShort("密码不正确");
            return;
        }
        APP.getInstance().mUserBean = list.get(0);
        mView.userBeanSuccess();
    }

    public void register(String phone, String pwd) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        long count = mUserBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.Username.eq(phone))
                .count();
        if (count > 0) {
            ToastUtils.showShort("用户名已存在");
            return;
        }
        UserBean userBean = new UserBean();
        userBean.setUsername(phone);
        userBean.setPassword(pwd);
        mUserBeanDao.insert(userBean);
        APP.getInstance().mUserBean = userBean;
        mView.userBeanSuccess();
    }

    public void resetPwd(String phone, String oldPwd, String newPwd, String newPwd2) {
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
        List<UserBean> list = mUserBeanDao.queryBuilder()
                .where(UserSQLDao.Properties.Username.eq(phone))
                .build().list();
        if (list == null || list.size() == 0) {
            ToastUtils.showShort("用户不存在");
            return;
        }
        UserBean userBean = list.get(0);
        if (!userBean.getPassword().equals(oldPwd)) {
            ToastUtils.showShort("旧密码不正确");
            return;
        }
        userBean.setPassword(newPwd);
        mUserBeanDao.update(userBean);
        APP.getInstance().mUserBean = userBean;
        mView.userBeanSuccess();

    }
}
