package com.account.box.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.account.box.APP;
import com.account.box.R;
import com.account.box.bean.AccountBean;
import com.account.box.bean.AccountBeanDao;
import com.account.box.bean.GroupBean;
import com.account.box.bean.GroupBeanDao;
import com.account.box.bean.UserBeanDao;
import com.account.box.utils.AddDialog;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.JJsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends JJsActivity {


    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;
    @BindView(R.id.navigation_left)
    NavigationView mNavigationLeft;
    @BindView(R.id.drawer)
    DrawerLayout mDrawer;

    @BindView(R.id.iv_float)
    FloatingActionButton mIvFloat;

    //数据库操作层
    GroupBeanDao mGroupBeanDao;
    AccountBeanDao mAccountDao;
    UserBeanDao mUserBeanDao;
    //账户数据
    List<GroupBean> mGroupBeanList;


    public static void open(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder=ButterKnife.bind(this);
        hasExitDouble();
        //设置toolbar
        mTool.setSubtitle("账号列表");
        setSupportActionBar(mTool);
        mTool.setNavigationIcon(R.drawable.main_default_avatar);
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否打开侧边栏进行关闭，其实不需要、直接打开即可
                mDrawer.openDrawer(Gravity.LEFT);
            }
        });
        LogUtils.e("查询开始");
        //设置刷新控件
        mSwipe.setColorSchemeColors(Color.parseColor("#FF4081"), Color.parseColor("#303F9F"), Color.parseColor("#33FFFF"));
        mSwipe.setRefreshing(true);
        //获取数据库操作层
        mGroupBeanDao = APP.getInstance().getDaoSession().getGroupBeanDao();
        mAccountDao = APP.getInstance().getDaoSession().getAccountBeanDao();
        //查询所有
        mGroupBeanList = mGroupBeanDao.queryBuilder()
                .where(GroupBeanDao.Properties.UserId.eq(APP.getInstance().mUserBean.getId()))
                .build().list();
        //查询完毕关闭加载动画
        mSwipe.setRefreshing(false);
        //查看数据
        LogUtils.e(mGroupBeanList.toString());
        List<AccountBean> lists = mAccountDao.queryBuilder()
                .where(AccountBeanDao.Properties.UserId.eq(APP.getInstance().mUserBean.getId()))
                .build().list();
        LogUtils.e(lists.toString());
        mUserBeanDao = APP.getInstance().getDaoSession().getUserBeanDao();
        getAccountList();
    }

    private void getAccountList() {
        APP.getInstance().mUserBean = mUserBeanDao.queryBuilder().where(UserBeanDao.Properties.Id.eq(APP.getInstance().mUserBean.getId())).unique();
        LogUtils.e("base===============" + APP.getInstance().mUserBean.getBaseAccountList().toString());
        LogUtils.e("group================" + APP.getInstance().mUserBean.getGroupList().toString());
        mSwipe.setRefreshing(false);
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


    @OnClick({R.id.iv_float})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_float:
                //打开添加dialog
                new AddDialog(this)
                        .setOnChangeListener(new AddDialog.OnChangeListner() {
                            @Override
                            public void onChange(int type) {
                                if (type == 0) {
                                    ToastUtils.showShort("账号已添加");
                                } else if (type == 1) {
                                    ToastUtils.showShort("分组已添加");
                                }
                                getAccountList();
                            }
                        })
                        .show();
                break;
        }
    }
}
