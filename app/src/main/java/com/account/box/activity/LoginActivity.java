package com.account.box.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.account.box.APP;
import com.account.box.R;
import com.account.box.Store;
import com.account.box.activity.persenter.LoginPersenter;
import com.account.box.activity.view.LoginView;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jjs.base.JJsActivity;
import com.jjs.base.Permission.PermissionSteward;
import com.jjs.base.utils.GlideUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明：
 * Created by aa on 2017/8/10.
 */

public class LoginActivity extends JJsActivity<LoginPersenter> implements LoginView {

    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.iv_head_22)
    ImageView ivHead22;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.iv_head_33)
    ImageView ivHead33;
    @BindView(R.id.iv_username)
    ImageView ivUsername;
    @BindView(R.id.edit_userAccount)
    TextInputEditText editUserAccount;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.edit_userPassword)
    TextInputEditText editUserPassword;
    @BindView(R.id.iv_clear)
    ImageView ivClear;

    public static void open(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUnbinder=ButterKnife.bind(this);
        mPersenter = new LoginPersenter(this);
        if (APP.isDebug) {
            mPersenter.login("jjs", "123456", "");
        }

        //设置toolbar
        mTool.setSubtitle("登陆");
        mTool.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(mTool);

        //监听焦点变化改变图片颜色
        editUserAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ivUsername.setColorFilter(getResources().getColor(R.color.colorBtn));
                } else {
                    ivUsername.setColorFilter(getResources().getColor(R.color.colorGray));
                }
            }
        });
        //监听焦点变化改变图片颜色和22，33娘图片
        editUserPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ivPassword.setColorFilter(getResources().getColor(R.color.colorBtn));
                    ivHead22.setImageResource(R.drawable.ic_22_hide);
                    ivHead33.setImageResource(R.drawable.ic_33_hide);
                } else {
                    ivPassword.setColorFilter(getResources().getColor(R.color.colorGray));
                    ivHead22.setImageResource(R.drawable.ic_22);
                    ivHead33.setImageResource(R.drawable.ic_33);
                }
            }
        });
        //监听输入设置清除按钮先隐藏
        editUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    ivClear.setVisibility(View.VISIBLE);
                } else {
                    ivClear.setVisibility(View.GONE);
                }
            }
        });
        //监听输入设置头像信息
        editUserAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    if (s.toString().length() > 0) {
                        //有数据时，查询本地保存头像，有头像时动态修改avatar内容进行展示，类QQ
                        File avatarFile = new File(APP.getInstance().getAvatarFile(), s.toString());
                        if (avatarFile.exists()) {
                            Glide.with(LoginActivity.this).load(avatarFile).apply(new RequestOptions().transform(new GlideUtils.CircleTransform())).into(ivAvatar);
                        }
                    }
                }
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有这个权限
            //进行sd卡读写权限的申请
            PermissionSteward.requestPermission(this, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

    }


    @Override
    public void onActivityResult(int i, Intent intent) {
        //从注册界面返回成功，需要关闭界面
        if (i == Store.Login.openRegisterCode) {
            finish();
        }
    }

    @Override
    public void onPermissionFailed(int i, List list) {

    }

    @Override
    public void onPermissionSucceed(int i, List list) {

    }


    @OnClick({R.id.iv_clear, R.id.sv_toRegister, R.id.sv_toLogin, R.id.tv_toReset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                editUserPassword.setText("");
                break;
            case R.id.sv_toRegister:
                RegisterActivity.open(this);
                break;
            case R.id.sv_toLogin:
                mPersenter.login(editUserAccount.getText().toString(), editUserPassword.getText().toString(), "");
                break;
            case R.id.tv_toReset:

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
        //关闭本界面
        ToastUtils.showShort("注册成功");
        setResult(Store.TAG.RESULT_OK);
        finish();
    }

    @Override
    public void sendSmsSuccess(String code) {
        ToastUtils.showShort("短信发送成功");
    }
}
