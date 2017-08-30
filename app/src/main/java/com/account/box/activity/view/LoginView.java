package com.account.box.activity.view;

import com.jjs.base.mvp.BaseView;

/**
 * 说明：
 * Created by aa on 2017/8/17.
 */

public interface LoginView extends BaseView {
    void userBeanSuccess();

    void sendSmsSuccess(String code);


}
