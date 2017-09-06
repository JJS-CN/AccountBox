package com.account.box.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.account.box.R;
import com.account.box.Store;
import com.account.box.activity.persenter.LoginPersenter;
import com.account.box.activity.view.LoginView;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.JJsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明：
 * Created by aa on 2017/8/10.
 */

public class RegisterActivity extends JJsActivity<LoginPersenter> implements LoginView {

    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.iv_head_22)
    ImageView mIvHead22;
    @BindView(R.id.iv_head_33)
    ImageView mIvHead33;
    @BindView(R.id.iv_username)
    ImageView mIvUsername;
    @BindView(R.id.edit_userAccount)
    TextInputEditText mEditUserAccount;
    @BindView(R.id.iv_password)
    ImageView mIvPassword;
    @BindView(R.id.edit_userPassword)
    TextInputEditText mEditUserPassword;
    @BindView(R.id.iv_clear)
    ImageView mIvClear;


    public static void open(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivityForResult(intent, Store.Login.openRegisterCode);
    }


    @Override
    public void onCreateView(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPersenter = new LoginPersenter(this);
        //设置toolbar
        mTool.setSubtitle("注册");
        setSupportActionBar(mTool);

        mTool.setNavigationIcon(R.drawable.ic_back);
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //监听焦点变化改变图片颜色
        mEditUserAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mIvUsername.setColorFilter(getResources().getColor(R.color.colorBtn));
                } else {
                    mIvUsername.setColorFilter(getResources().getColor(R.color.colorGray));
                }
            }
        });
        //监听焦点变化改变图片颜色和22，33娘图片
        mEditUserPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mIvPassword.setColorFilter(getResources().getColor(R.color.colorBtn));
                    mIvHead22.setImageResource(R.drawable.ic_22_hide);
                    mIvHead33.setImageResource(R.drawable.ic_33_hide);
                } else {
                    mIvPassword.setColorFilter(getResources().getColor(R.color.colorGray));
                    mIvHead22.setImageResource(R.drawable.ic_22);
                    mIvHead33.setImageResource(R.drawable.ic_33);
                }
            }
        });
        //监听输入设置清除按钮先隐藏
        mEditUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mIvClear.setVisibility(View.VISIBLE);
                } else {
                    mIvClear.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onActivityResult(int i, Intent intent) {

    }

    @Override
    public void onPermissionFailed(int i, List list) {

    }

    @Override
    public void onPermissionSucceed(int i, List list) {

    }


    @OnClick({R.id.iv_clear, R.id.sv_toRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                mEditUserPassword.setText("");
                break;
            case R.id.sv_toRegister:
                mPersenter.register(mEditUserAccount.getText().toString(), mEditUserPassword.getText().toString());
                break;
        }
    }

    @Override
    public void ResponseSuccess(int i, String s) {

    }

    @Override
    public void userBeanSuccess() {
        //打开主界面
        MainActivity.open(this);
        //关闭登陆注册界面
        ToastUtils.showShort("注册成功");
        setResult(Store.TAG.RESULT_OK);
        finish();
    }

    @Override
    public void sendSmsSuccess(String code) {
        ToastUtils.showShort("短信发送成功");
    }
}
