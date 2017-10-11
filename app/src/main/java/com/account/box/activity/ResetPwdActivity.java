package com.account.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.account.box.R;
import com.blankj.utilcode.util.StringUtils;
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
            ToastUtils.showShort("2次新密码不正确");
            return;
        }

        boolean isOldPwd = false;
       /* for (int i = 0; i < mRadioTypePwd.getChildCount(); i++) {
            switch (i) {
                case 0:
                    //原始密码为空且为当前选中项，或者不为空且匹配成功
                    //个人
                    if (TextUtils.isEmpty(APP.getInstance().mUserBean.getPasswordPrivate()) && mRadioTypePwd.getChildAt(i).getId() == mRadioTypePwd.getCheckedRadioButtonId()
                            || !TextUtils.isEmpty(APP.getInstance().mUserBean.getPasswordPrivate()) && APP.getInstance().mUserBean.getPasswordPrivate().equals(EncryptUtils.encryptMD5ToString(oldPwd))) {
                        isOldPwd = true;
                        APP.getInstance().mUserBean.setPasswordPrivate(EncryptUtils.encryptMD5ToString(newPwd1));
                    }
                    break;
                case 1:
                    //组员
                    if (TextUtils.isEmpty(APP.getInstance().mUserBean.getPasswordProtected()) && mRadioTypePwd.getChildAt(i).getId() == mRadioTypePwd.getCheckedRadioButtonId()
                            || !TextUtils.isEmpty(APP.getInstance().mUserBean.getPasswordProtected()) && APP.getInstance().mUserBean.getPasswordProtected().equals(EncryptUtils.encryptMD5ToString(oldPwd))) {
                        isOldPwd = true;
                        APP.getInstance().mUserBean.setPasswordProtected(EncryptUtils.encryptMD5ToString(newPwd1));
                    }
                    break;
                case 2:
                    //公开
                    if (TextUtils.isEmpty(APP.getInstance().mUserBean.getPasswordPublic()) && mRadioTypePwd.getChildAt(i).getId() == mRadioTypePwd.getCheckedRadioButtonId()
                            || !TextUtils.isEmpty(APP.getInstance().mUserBean.getPasswordPublic()) && APP.getInstance().mUserBean.getPasswordPublic().equals(EncryptUtils.encryptMD5ToString(oldPwd))) {
                        isOldPwd = true;
                        APP.getInstance().mUserBean.setPasswordPublic(EncryptUtils.encryptMD5ToString(newPwd1));
                    }
                    break;
            }
            //校验到选中项,或者匹配通过，不处理之后的低级密码
            if (isOldPwd || mRadioTypePwd.getChildAt(i).getId() == mRadioTypePwd.getCheckedRadioButtonId()) {
                break;
            }
        }*/
        if (!isOldPwd) {
            ToastUtils.showShort("旧密码不正确");
            return;
        }
        //APP.getInstance().getDaoSession().getUserBeanDao().update(APP.getInstance().mUserBean);
        ToastUtils.showShort("修改密码成功");
        finish();
    }
}
