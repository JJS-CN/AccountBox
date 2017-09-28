package com.account.box.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.account.box.APP;
import com.account.box.R;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.JJsActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 说明：修改密码界面
 * Created by Administrator on 2017/9/29.
 */

public class ResetPwdActivity extends JJsActivity {

    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.radio_type_pwd)
    RadioGroup mRadioTypePwd;
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
        mTool.setSubtitle("修改密码");
        setSupportActionBar(mTool);
        mTool.setNavigationIcon(R.drawable.ic_back);
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for (int i = 0; i < mRadioTypePwd.getChildCount(); i++) {
            RadioButton btn = (RadioButton) mRadioTypePwd.getChildAt(i);
            if (APP.getInstance().mLoginType == i) {
                btn.performClick();
                break;
            } else {
                btn.setClickable(false);
                btn.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                btn.setTextColor(getResources().getColor(R.color.Hint));
            }
        }
    }

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }

    @OnClick(R.id.tv_reset)
    public void onViewClicked() {
        String oldPwd = mEditPwdOld.getText().toString().trim();
        String newPwd1 = mEditPwdNew1.getText().toString().trim();
        String newPwd2 = mEditPwdNew2.getText().toString().trim();
        //高级密码同样可以重置
        if (mRadioTypePwd.getChildAt(0).getId() == mRadioTypePwd.getCheckedRadioButtonId()) {
            //个人
            if (!TextUtils.isEmpty(APP.getInstance().mUserBean.getPasswordPrivate()) && !APP.getInstance().mUserBean.getPasswordPrivate().equals(oldPwd)) {
                ToastUtils.showShort("旧密码不正确");
                return;
            }
        } else if (mRadioTypePwd.getChildAt(1).getId() == mRadioTypePwd.getCheckedRadioButtonId()) {
            //组员

        } else if (mRadioTypePwd.getChildAt(2).getId() == mRadioTypePwd.getCheckedRadioButtonId()) {
            //公开

        }

    }
}
