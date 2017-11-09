package com.account.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;

import com.account.box.APP;
import com.account.box.R;
import com.account.box.bean.RxResult;
import com.account.box.http.ApiService;
import com.account.box.http.RxObserver;
import com.account.box.utils.ToolUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.base.BaseActivity;
import com.jjs.base.http.RetrofitUtils;
import com.jjs.base.http.RxSchedulers;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 说明：修改密码界面
 * Created by Administrator on 2017/9/29.
 */

public class ResetPwdActivity extends BaseActivity {

    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.edit_pwd_old)
    TextInputEditText mEditPwdOld;
    @BindView(R.id.edit_pwd_new1)
    TextInputEditText mEditPwdNew1;
    @BindView(R.id.edit_pwd_new2)
    TextInputEditText mEditPwdNew2;

    public static void open(Context context) {
        Intent intent = new Intent(context, ResetPwdActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ToolUtils.initTool(this, mTool, "修改密码", true);
    }

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }

    @OnClick(R.id.tv_reset)
    public void onViewClicked() {
        String oldPwd = mEditPwdOld.getText().toString().trim();
        String newPwd1 = mEditPwdNew1.getText().toString().trim();
        String newPwd2 = mEditPwdNew2.getText().toString().trim();

        if (StringUtils.isEmpty(newPwd1) || StringUtils.isEmpty(newPwd2)) {
            ToastUtils.showShort("新密码不能为空");
            return;
        }
        if (!newPwd1.equals(newPwd2)) {
            ToastUtils.showShort("2次新密码不一致");
            return;
        }

        RetrofitUtils.getInstance()
                .create(ApiService.User.class)
                .changePassword(APP.getInstance().mUserBean.getUser().getAccount(), oldPwd, newPwd1)
                .compose(RxSchedulers.getInstance(this.bindToLifecycle()).<RxResult<String>>io_main())
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void _onSuccess(String s) {
                        ToastUtils.showShort("修改密码成功");
                        LoginActivity.open(ResetPwdActivity.this);
                    }
                });

    }
}
