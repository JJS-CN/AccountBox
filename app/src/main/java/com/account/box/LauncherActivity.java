package com.account.box;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.account.box.activity.LoginActivity;
import com.account.box.activity.MainActivity;
import com.blankj.utilcode.util.SPUtils;
import com.jjs.base.base.BaseLauncherActivity;

/**
 * 说明：初次启动。启动页
 * Created by aa on 2017/8/8.
 */

public class LauncherActivity extends BaseLauncherActivity {


    @Override
    protected int setSplshBackground() {
        return 0;
    }

    @Override
    protected void isFirst() {
    }

    @Override
    protected void notFirst() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SPUtils.getInstance("app").getBoolean(Store.Login.isLogin, false)) {
            MainActivity.open(this);
        } else {
            LoginActivity.open(this);
        }
        finish();

    }
}
