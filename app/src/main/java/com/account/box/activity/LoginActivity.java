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
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.account.box.R;
import com.account.box.Store;
import com.account.box.activity.persenter.LoginPersenter;
import com.account.box.activity.view.LoginView;
import com.account.box.utils.GlideApp;
import com.account.box.utils.ToolUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.base.BaseActivity;
import com.jjs.base.utils.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 说明：
 * Created by aa on 2017/8/10.
 */

public class LoginActivity extends BaseActivity<LoginPersenter> implements LoginView {

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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPersenter = new LoginPersenter(this);
        hasExitDouble();
        //设置toolbar
        mTool.setNavigationIcon(R.drawable.nulls);
        ToolUtils.initTool(this, mTool, "登录", false);

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
                    if (s.toString().trim().length() > 0) {
                        if (!TextUtils.isEmpty(s.toString().trim())) {
                            String avatar = SPUtils.getInstance().getString(s.toString().trim() + "avatar");
                            if (!TextUtils.isEmpty(avatar)) {
                                GlideApp.with(LoginActivity.this).load(avatar).transform(new GlideUtils.CircleTransform()).error(R.drawable.main_default_avatar).into(ivAvatar);
                            } else {
                                ivAvatar.setImageResource(R.drawable.bili_default_avatar);
                            }
                        }
                    }

                }
            }
        });
        editUserAccount.setText(SPUtils.getInstance().getString("username"));

    }


    @Override
    public void onActivityResult(int i, Intent intent) {
        //从注册界面返回成功，需要关闭界面
        if (i == Store.Login.openRegisterCode) {
            editUserAccount.setText(SPUtils.getInstance().getString("username"));
            editUserPassword.setText("");
        }
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
                PermissionUtils.requestPermissions(this, 1, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        TelephonyManager TelephonyMgr = (TelephonyManager) LoginActivity.this.getSystemService(TELEPHONY_SERVICE);
                        String imei = "";
                        try {
                            imei = TelephonyMgr.getDeviceId();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mPersenter.login(editUserAccount.getText().toString(), editUserPassword.getText().toString(), imei);
                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {
                    }
                });

                break;
            case R.id.tv_toReset:
                ToastUtils.showShort("请联系：994462623@qq.com\n        或：188633921@qq.com");
                break;
        }
    }

    @Override
    public void ResponseSuccess(int i, String s) {

    }

    @Override
    public void userBeanSuccess() {
        //打开主界面
        MainActivity.openPair(this, ivAvatar);
        //关闭本界面
        ToastUtils.showShort("登录成功");
        setResult(Store.TAG.RESULT_OK);
        finish();
    }

    @Override
    public void sendSmsSuccess(String code) {
        ToastUtils.showShort("短信发送成功");
    }
}
