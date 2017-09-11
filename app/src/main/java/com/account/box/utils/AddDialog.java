package com.account.box.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.account.box.R;
import com.blankj.utilcode.util.ToastUtils;

/**
 * 说明：
 * Created by Administrator on 2017/9/11.
 */

public class AddDialog extends Dialog {
    ImageView mIvClose;
    RadioGroup mRadioType;
    TextInputEditText mEditAccountAccount;
    TextInputEditText mEditAccountPassword;
    TextInputEditText mEditAccountMessage;
    TextView mTvOpenGroup;
    LinearLayout mLlOpenGroup;
    RecyclerView mRvGroupList;
    LinearLayout mLlAccount;
    TextInputEditText mEditGroupName;
    LinearLayout mLlGroup;

    CardView mCheckAccount;
    CardView mCheckGroup;

    public AddDialog(@NonNull Context context) {
        super(context,R.style.MyDialog);
        init(context);
    }

    public AddDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected AddDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }


    private void init(Context mContext) {
        View view = View.inflate(mContext, R.layout.dialog_account_add, null);
        mIvClose = (ImageView) view.findViewById(R.id.iv_close);
        mRadioType = (RadioGroup) view.findViewById(R.id.radio_type);
        mLlAccount = (LinearLayout) view.findViewById(R.id.ll_account);
        mLlGroup = (LinearLayout) view.findViewById(R.id.ll_group);

        mEditAccountAccount = (TextInputEditText) view.findViewById(R.id.edit_account_Account);
        mEditAccountPassword = (TextInputEditText) view.findViewById(R.id.edit_account_Password);
        mEditAccountMessage = (TextInputEditText) view.findViewById(R.id.edit_account_Message);
        mLlOpenGroup = (LinearLayout) view.findViewById(R.id.ll_open_group);
        mTvOpenGroup = (TextView) view.findViewById(R.id.tv_open_group);
        mRvGroupList = (RecyclerView) view.findViewById(R.id.rv_group_list);

        mEditGroupName = (TextInputEditText) view.findViewById(R.id.edit_group_name);
        mCheckAccount = (CardView) view.findViewById(R.id.card_check_account);
        mCheckGroup = (CardView) view.findViewById(R.id.card_check_group);

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog.this.dismiss();
            }
        });
        mRadioType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == group.getChildAt(0).getId()) {
                    mLlAccount.setVisibility(View.VISIBLE);
                    mLlGroup.setVisibility(View.GONE);
                } else if (checkedId == group.getChildAt(1).getId()) {
                    mLlAccount.setVisibility(View.GONE);
                    mLlGroup.setVisibility(View.VISIBLE);
                }

            }
        });
        mRadioType.check(mRadioType.getChildAt(0).getId());
        mCheckAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountName = mEditAccountAccount.getText().toString().trim();
                String accountPwd = mEditAccountPassword.getText().toString().trim();
                String accountMsg = mEditAccountMessage.getText().toString().trim();
                String accountGroup = mTvOpenGroup.getText().toString().trim();
                if (TextUtils.isEmpty(accountName)) {
                    ToastUtils.showShort("用户名不能为空");
                    return;
                } else if (TextUtils.isEmpty(accountPwd)) {
                    ToastUtils.showShort("密码不能为空");
                    return;
                } else if (TextUtils.isEmpty(accountGroup)) {
                    ToastUtils.showShort("所属分组不能为空");
                    return;
                }
                //先判断是否有重复的，再进行添加操作！！！


            }
        });
        mCheckGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = mEditGroupName.getText().toString().trim();
                if (TextUtils.isEmpty(groupName)) {
                    ToastUtils.showShort("分组名称不能为空");
                }
                //先判断是否有重复的，再进行添加操作！！！


            }
        });
        mLlOpenGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRvGroupList.setVisibility(mRvGroupList.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
        this.setContentView(view);
     this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);// 点击Dialog外部消失

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width =  mContext.getResources().getDisplayMetrics().widthPixels*4/5; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);

    }
}
