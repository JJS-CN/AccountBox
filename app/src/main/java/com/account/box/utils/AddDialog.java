package com.account.box.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.account.box.APP;
import com.account.box.R;
import com.account.box.bean.AccountBean;
import com.account.box.bean.GroupBean;
import com.account.box.bean.RxResult;
import com.account.box.http.ApiService;
import com.account.box.http.RxObserver;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jjs.base.http.RetrofitUtils;
import com.jjs.base.http.RxSchedulers;
import com.jjs.base.utils.recyclerview.QuickAdapter;
import com.jjs.base.utils.recyclerview.QuickHolder;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/**
 * 说明：
 * Created by Administrator on 2017/9/11.
 */

public class AddDialog extends Dialog {

    Context mContext;
    //数据库操作层
    List<GroupBean> mGroupBeanList;

    //状态改变监听，通知主界面需要更新数据
    OnChangeListner mChangeListner;
    GroupBean checkListBean;//选中分组
    int checkTypePwd;//选中级别

    TextView mTvTopTitle;//标题
    LinearLayout mLlAccountType;//账户类型

    ImageView mIvClose;
    RadioGroup mRadioType;

    TextInputEditText mEditAccountTitle;
    TextInputEditText mEditAccountMessage;
    TextInputEditText mEditAccountAccount;
    TextInputEditText mEditAccountPassword;

    TextView mTvOpenGroup;
    LinearLayout mLlOpenGroup;
    RecyclerView mRvGroupList;
    LinearLayout mLlAccount;
    TextInputEditText mEditGroupName;
    LinearLayout mLlGroup;

    CardView mCheckAccount;
    CardView mCheckGroup;

    public AddDialog(@NonNull RxAppCompatActivity context, AccountBean accountBean) {
        super(context, R.style.MyDialog);
        init(context, accountBean);
    }

    public AddDialog(@NonNull RxAppCompatActivity context, AccountBean accountBean, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context, accountBean);
    }

    protected AddDialog(@NonNull RxAppCompatActivity context, AccountBean accountBean, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context, accountBean);
    }


    private void init(final RxAppCompatActivity mContext, final AccountBean accountBean) {
        this.mContext = mContext;
        //数据库操作层
        //查询数据
        mGroupBeanList = APP.getInstance().mUserBean.getGroups();
        LogUtils.e(mGroupBeanList.toString());
        //布局实例化
        View view = View.inflate(mContext, R.layout.dialog_account_add, null);
        mTvTopTitle = (TextView) view.findViewById(R.id.tv_top_title);
        mLlAccountType = (LinearLayout) view.findViewById(R.id.ll_account_type);

        mIvClose = (ImageView) view.findViewById(R.id.iv_close);
        mRadioType = (RadioGroup) view.findViewById(R.id.radio_type);
        mLlAccount = (LinearLayout) view.findViewById(R.id.ll_account);
        mLlGroup = (LinearLayout) view.findViewById(R.id.ll_group);

        mEditAccountTitle = (TextInputEditText) view.findViewById(R.id.edit_account_Title);
        mEditAccountMessage = (TextInputEditText) view.findViewById(R.id.edit_account_Message);
        mEditAccountAccount = (TextInputEditText) view.findViewById(R.id.edit_account_Account);
        mEditAccountPassword = (TextInputEditText) view.findViewById(R.id.edit_account_Password);

        mLlOpenGroup = (LinearLayout) view.findViewById(R.id.ll_open_group);
        mTvOpenGroup = (TextView) view.findViewById(R.id.tv_open_group);
        mRvGroupList = (RecyclerView) view.findViewById(R.id.rv_group_list);

        mEditGroupName = (TextInputEditText) view.findViewById(R.id.edit_group_name);
        mCheckAccount = (CardView) view.findViewById(R.id.card_check_account);
        mCheckGroup = (CardView) view.findViewById(R.id.card_check_group);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInputMethod();
            }
        });

        //设置list
        if (mGroupBeanList.size() > 0) {
            mTvOpenGroup.setText(mGroupBeanList.get(0).getGroup_name());
            checkListBean = mGroupBeanList.get(0);
        }
        mRvGroupList.setLayoutManager(new LinearLayoutManager(mContext));
        QuickAdapter quickAdapter = new QuickAdapter<GroupBean>(R.layout.recycler_group_list, mGroupBeanList) {
            @Override
            public void _convert(QuickHolder quickHolder, GroupBean accountListBean) {
                quickHolder.setText(R.id.tv_group_name, accountListBean.getGroup_name());
            }
        };
        quickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mRvGroupList.setVisibility(View.GONE);
                mTvOpenGroup.setText(mGroupBeanList.get(position).getGroup_name());
                checkListBean = mGroupBeanList.get(position);
                hideInputMethod();
            }
        });
        mRvGroupList.setAdapter(quickAdapter);

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog.this.dismiss();
            }
        });

        mRadioType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                hideInputMethod();
                if (checkedId == group.getChildAt(0).getId()) {
                    mLlAccount.setVisibility(View.VISIBLE);
                    mLlGroup.setVisibility(View.GONE);
                } else if (checkedId == group.getChildAt(1).getId()) {
                    mLlAccount.setVisibility(View.GONE);
                    mLlGroup.setVisibility(View.VISIBLE);
                }

            }
        });
        if (mGroupBeanList.size() > 0) {
            mRadioType.getChildAt(0).performClick();
        } else {
            mRadioType.getChildAt(1).performClick();
            mRadioType.getChildAt(0).setClickable(false);
        }


        /**
         * 对修改情况做处理
         */
        if (accountBean != null) {
            mTvTopTitle.setText("Update");
            mLlAccountType.setVisibility(View.GONE);
            mRadioType.getChildAt(0).performClick();//默认选中账户
            mEditAccountTitle.setText(accountBean.getTitle());
            mEditAccountAccount.setText(accountBean.getAccount_name());//账户名
            mEditAccountPassword.setText(accountBean.getPassword());//账户密码
            mEditAccountMessage.setText(accountBean.getRemark());//账户内容

            for (int i = 0; i < mGroupBeanList.size(); i++) {
                if (mGroupBeanList.get(i).getId().equals(accountBean.getGroup_id())) {
                    mTvOpenGroup.setText(mGroupBeanList.get(i).getGroup_name());
                    checkListBean = mGroupBeanList.get(i);
                    break;
                }
            }
        }
        mCheckAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInputMethod();
                String accountTitle = mEditAccountTitle.getText().toString().trim();
                String accountName = mEditAccountAccount.getText().toString().trim();
                String accountPwd = mEditAccountPassword.getText().toString().trim();
                String accountMsg = mEditAccountMessage.getText().toString().trim();
                if (TextUtils.isEmpty(accountTitle)) {
                    ToastUtils.showShort("标题不能为空");
                    return;
                } else if (TextUtils.isEmpty(accountName)) {
                    ToastUtils.showShort("用户名不能为空");
                    return;
                } else if (TextUtils.isEmpty(accountPwd)) {
                    ToastUtils.showShort("密码不能为空");
                    return;
                } else if (checkListBean == null) {
                    ToastUtils.showShort("所属分组不能为空");
                    return;
                }
                if (accountBean == null) {
                    //先判断是否有重复的，再进行添加操作！！！
                    RetrofitUtils.getInstance().create(ApiService.Account.class)
                            .addAccountToGroup(checkListBean.getId(), accountTitle, accountName, accountPwd, accountMsg)
                            .compose(RxSchedulers.getInstance(mContext.bindToLifecycle()).showLoading(true).<RxResult<String>>io_main())
                            .subscribe(new RxObserver<String>() {
                                @Override
                                protected void _onSuccess(String msg) {
                                    if (mChangeListner != null) {
                                        mChangeListner.onChange(0);
                                    }
                                    dismiss();
                                    ToastUtils.showShort("账号已添加");
                                }
                            });
                } else {
                    RetrofitUtils.getInstance().create(ApiService.Account.class)
                            .updateAccount(accountBean.getId(), accountBean.getGroup_id(), accountTitle, accountName, accountPwd, accountMsg)
                            .compose(RxSchedulers.getInstance(mContext.bindToLifecycle()).showLoading(true).<RxResult<AccountBean>>io_main())
                            .subscribe(new RxObserver<AccountBean>() {
                                @Override
                                protected void _onSuccess(AccountBean account) {
                                    if (mChangeListner != null) {
                                        mChangeListner.onChange(1);
                                    }
                                    dismiss();
                                    ToastUtils.showShort("账号已更新");
                                }
                            });
                }


            }
        });
        mCheckGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = mEditGroupName.getText().toString().trim();
                if (TextUtils.isEmpty(groupName)) {
                    ToastUtils.showShort("分组名称不能为空");
                    return;
                }
                RetrofitUtils.getInstance().create(ApiService.Account.class)
                        .createGroup(APP.getInstance().mUserBean.getUser().getId(), groupName)
                        .compose(RxSchedulers.getInstance(mContext.bindToLifecycle()).showLoading(true).<RxResult<String>>io_main())
                        .subscribe(new RxObserver<String>() {
                            @Override
                            protected void _onSuccess(String msg) {
                                if (mChangeListner != null) {
                                    mChangeListner.onChange(2);
                                }
                                dismiss();
                                ToastUtils.showShort("分组已添加");
                            }
                        });
            }
        });
        mLlOpenGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRvGroupList.setVisibility(mRvGroupList.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                hideInputMethod();
            }
        });
        this.setContentView(view);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);// 点击Dialog外部消失

        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = mContext.getResources().getDisplayMetrics().widthPixels * 4 / 5; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);

    }

    private void hideInputMethod() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public AddDialog setOnChangeListener(OnChangeListner changeListener) {
        this.mChangeListner = changeListener;
        return this;
    }

    public interface OnChangeListner {
        void onChange(int type);
    }
}
