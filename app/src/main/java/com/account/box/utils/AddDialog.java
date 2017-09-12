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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.account.box.APP;
import com.account.box.R;
import com.account.box.bean.AccountBean;
import com.account.box.bean.AccountBeanDao;
import com.account.box.bean.AccountListBean;
import com.account.box.bean.AccountListBeanDao;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jjs.base.utils.recyclerview.QuickAdapter;
import com.jjs.base.utils.recyclerview.QuickHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 * Created by Administrator on 2017/9/11.
 */

public class AddDialog extends Dialog {
    //数据库操作层
    AccountListBeanDao mAccountListDao;
    AccountBeanDao mAccountDao;
    List<AccountListBean> mAccountList;

    //状态改变监听，通知主界面需要更新数据
    OnChangeListner mChangeListner;
    AccountListBean checkListBean;//选中分组

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

    public AddDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
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


    private void init(final Context mContext) {
        //数据库操作层
        mAccountListDao = APP.getInstance().getDaoSession().getAccountListBeanDao();
        mAccountDao = APP.getInstance().getDaoSession().getAccountBeanDao();
        //查询数据
        mAccountList = new ArrayList<>();
        mAccountList.add(new AccountListBean(0L, APP.getInstance().mUserBean.getId(), "根目录"));
        List<AccountListBean> lists = mAccountListDao.queryBuilder().where(AccountListBeanDao.Properties.UserId.eq(APP.getInstance().mUserBean.getId())).build().list();
        mAccountList.addAll(lists);
        LogUtils.e(mAccountList.toString());
        //布局实例化
        View view = View.inflate(mContext, R.layout.dialog_account_add, null);
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
        //设置list
        mRvGroupList.setLayoutManager(new LinearLayoutManager(mContext));
        QuickAdapter quickAdapter = new QuickAdapter<AccountListBean>(R.layout.recycler_group_list, mAccountList) {
            @Override
            public void _convert(QuickHolder quickHolder, AccountListBean accountListBean) {
                quickHolder.setText(R.id.tv_group_name, accountListBean.getName());
            }
        };
        quickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mRvGroupList.setVisibility(View.GONE);
                mTvOpenGroup.setText(mAccountList.get(position).getName());
                checkListBean = mAccountList.get(position);
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
                long count = mAccountDao.queryBuilder()
                        .where(AccountBeanDao.Properties.UserId.eq(APP.getInstance().mUserBean.getId())
                                , AccountBeanDao.Properties.AccoutTitle.eq(accountTitle)
                                , AccountBeanDao.Properties.AccountName.eq(accountName)
                                , AccountBeanDao.Properties.AccountPwd.eq(accountPwd))
                        .count();
                if (count != 0) {
                    ToastUtils.showShort("数据重复，请重新输入");
                    return;
                }
                //先判断是否有重复的，再进行添加操作！！！
                AccountBean accountBean = new AccountBean();
                accountBean.setUserId(APP.getInstance().mUserBean.getId());
                accountBean.setAccoutTitle(accountTitle);
                accountBean.setAccountName(accountName);
                accountBean.setAccountPwd(accountPwd);
                accountBean.setAccountMsg(accountMsg);
                accountBean.setAccountListId(checkListBean.getId());
                long id = mAccountDao.insert(accountBean);
                if (id == 0) {
                    ToastUtils.showShort("新增失败");
                } else if (mChangeListner != null) {
                    mChangeListner.onChange(0);
                    dismiss();
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
                boolean isRepeat = false;
                for (AccountListBean listBean : mAccountList) {
                    if (listBean.getName().equals(groupName)) {
                        isRepeat = true;
                        break;
                    }
                }
                if (isRepeat) {
                    ToastUtils.showShort("分组名有重复，请重新输入");
                    return;
                }
                //先判断是否有重复的，再进行添加操作！！！
                AccountListBean groupBean = new AccountListBean();
                groupBean.setName(groupName);
                groupBean.setUserId(APP.getInstance().mUserBean.getId());
                long id = mAccountListDao.insert(groupBean);
                if (id == 0) {
                    ToastUtils.showShort("新增失败");
                } else if (mChangeListner != null) {
                    mChangeListner.onChange(1);
                    dismiss();
                }
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
        lp.width = mContext.getResources().getDisplayMetrics().widthPixels * 4 / 5; // 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);

    }

    public AddDialog setOnChangeListener(OnChangeListner changeListener) {
        this.mChangeListner = changeListener;
        return this;
    }

    public interface OnChangeListner {
        void onChange(int type);
    }
}
