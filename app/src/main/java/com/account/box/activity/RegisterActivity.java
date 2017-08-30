package com.account.box.activity;

import android.content.Context;
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
import com.jjs.base.JJsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明：
 * Created by aa on 2017/8/10.
 */

public class RegisterActivity extends JJsActivity {

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

    public static void open(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreateView(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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
                break;
            case R.id.sv_toRegister:
                break;
        }
    }

}
