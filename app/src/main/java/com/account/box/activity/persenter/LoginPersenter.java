package com.account.box.activity.persenter;

import android.text.TextUtils;

import com.account.box.APP;
import com.account.box.Store;
import com.account.box.activity.view.LoginView;
import com.account.box.bean.UserBean;
import com.account.box.bean.UserBeanDao;
import com.account.box.utils.RsaUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.mvp.BasePersenter;

import java.lang.reflect.Field;
import java.security.KeyPair;

/**
 * 说明：
 * Created by aa on 2017/8/17.
 */

public class LoginPersenter extends BasePersenter<LoginView> {
    private UserBeanDao mUserBeanDao;


    public LoginPersenter(LoginView view) {
        super(view);
        mUserBeanDao = APP.getInstance().getDaoSession().getUserBeanDao();
    }

    public void sendSms(String phone) {
    }

    public void login(String name, String pwd, String sms) {
        UserBean userBean = mUserBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.Username.eq(name))
                .build().unique();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        if (userBean == null) {
            ToastUtils.showShort("用户不存在");
            return;
        }
        String password = EncryptUtils.encryptMD5ToString(pwd);
        //如果密码为超级密码+姓名，就直接通过验证
        //否则判断3级密码登录，展示不同的密码效果
        int loginType = 0;
        if (pwd.equals(APP.getInstance().passwordSuper + name)) {
            ToastUtils.showShort("以超级密码登录，请尽快修改密码！");
        } else if (password.equals(userBean.getPasswordPrivate())) {
            loginType = Store.Password.Private;
        } else if (password.equals(userBean.getPasswordProtected())) {
            loginType = Store.Password.Protected;
        } else if (password.equals(userBean.getPasswordPublic())) {
            loginType = Store.Password.Public;
        } else {
            ToastUtils.showShort("密码不正确");
            return;
        }

        APP.getInstance().mUserBean = userBean;
        APP.getInstance().mLoginType = loginType;
        mView.userBeanSuccess();
        SPUtils.getInstance().put("username", name);
       // SPUtils.getInstance().put("password", pwd);
    }

    public void register(String name, String pwd) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        long count = mUserBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.Username.eq(name))
                .count();
        if (count > 0) {
            ToastUtils.showShort("用户名已存在");
            return;
        }
        UserBean userBean = new UserBean();
        userBean.setUsername(name);
        userBean.setPasswordPrivate(EncryptUtils.encryptMD5ToString(pwd));

        KeyPair keyPair = RsaUtils.generateRSAKeyPair();
        userBean.setRsaPublicKey(keyPair.getPublic().getEncoded());
        userBean.setRsaPrivateKey(keyPair.getPrivate().getEncoded());
        //生成rsa加密串
        mUserBeanDao.insert(userBean);
        APP.getInstance().mUserBean = userBean;
        APP.getInstance().mLoginType = Store.Password.Private;
        mView.userBeanSuccess();
        SPUtils.getInstance().put("username", name);
       // SPUtils.getInstance().put("password", pwd);
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

        UserBean userBean = mUserBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.Username.eq(phone))
                .build().unique();
        if (userBean == null) {
            ToastUtils.showShort("用户不存在");
            return;
        }
        switch (pwdType) {
            case Store.Password.Private:
                if (!TextUtils.isEmpty(userBean.getPasswordPrivate()) && !userBean.getPasswordPrivate().equals(EncryptUtils.encryptMD5ToString(oldPwd))) {
                    ToastUtils.showShort("旧密码不正确");
                    return;
                }
                userBean.setPasswordPrivate(EncryptUtils.encryptMD5ToString(newPwd));
                break;
            case Store.Password.Protected:
                if (!TextUtils.isEmpty(userBean.getPasswordProtected()) && !userBean.getPasswordProtected().equals(EncryptUtils.encryptMD5ToString(oldPwd))) {
                    ToastUtils.showShort("旧密码不正确");
                    return;
                }
                userBean.setPasswordProtected(EncryptUtils.encryptMD5ToString(newPwd));
                break;
            case Store.Password.Public:
                if (!TextUtils.isEmpty(userBean.getPasswordPublic()) && !userBean.getPasswordPublic().equals(EncryptUtils.encryptMD5ToString(oldPwd))) {
                    ToastUtils.showShort("旧密码不正确");
                    return;
                }
                userBean.setPasswordPublic(EncryptUtils.encryptMD5ToString(newPwd));
                break;
        }


        mUserBeanDao.update(userBean);
        APP.getInstance().mUserBean = userBean;
        mView.userBeanSuccess();
    }
}
