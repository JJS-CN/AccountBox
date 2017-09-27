package com.account.box.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.account.box.APP;
import com.account.box.R;
import com.account.box.bean.AccountBean;
import com.account.box.bean.AccountBeanDao;
import com.account.box.bean.GroupBean;
import com.account.box.bean.GroupBeanDao;
import com.account.box.utils.AddDialog;
import com.account.box.utils.RsaUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.JJsActivity;
import com.jjs.base.utils.recyclerview.QuickAdapter;
import com.jjs.base.utils.recyclerview.QuickHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.account.box.R.id.rv;

public class MainActivity extends JJsActivity {


    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(rv)
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

    int[] TitleColors = new int[]{
            Color.parseColor("#FFF5EE"), Color.parseColor("#FFDAB9"),
            Color.parseColor("#FFBBFF"), Color.parseColor("#BFEFFF"),
            Color.parseColor("#B9D3EE"), Color.parseColor("#9BCD9B"),
            Color.parseColor("#98FB98"), Color.parseColor("#C0FF3E"),
            Color.parseColor("#9AFF9A"), Color.parseColor("#9F79EE")};
    //数据库操作层
    GroupBeanDao mGroupBeanDao;
    AccountBeanDao mAccountBeanDao;
    //账户数据
    List<GroupBean> mGroupBeanList = new ArrayList<>();
    QuickAdapter mQuickAdapter;

    public static void open(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //执行各view的初始化操作
        initTitleAndRefresh();
        initRecyclerShow();

        //获取数据库操作层
        mGroupBeanDao = APP.getInstance().getDaoSession().getGroupBeanDao();
        mAccountBeanDao = APP.getInstance().getDaoSession().getAccountBeanDao();
        getAccountList();//执行查询操作

        hasExitDouble();//需要连点退出

    }

    private void initTitleAndRefresh() {
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
        //设置刷新控件
        mSwipe.setColorSchemeColors(Color.parseColor("#FF4081"), Color.parseColor("#303F9F"), Color.parseColor("#33FFFF"));
        mSwipe.setRefreshing(true);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAccountList();
            }
        });
        if (APP.getInstance().mLoginType!=0){
            mIvFloat.hide();
        }
    }

    private void initRecyclerShow() {
        //列表展示控件
        mQuickAdapter = new QuickAdapter<GroupBean>(R.layout.recycler_group_show, mGroupBeanList) {
            @Override
            public void _convert(QuickHolder quickHolder, final GroupBean groupBean) {
                TextView groupName = quickHolder.getView(R.id.tv_group_name);
                final RecyclerView rv = quickHolder.getView(R.id.rv_account_list);
                if (APP.getInstance().mLoginType > groupBean.getPasswordType()) {
                    groupName.setText("无权限");
                } else {
                    groupName.setText(groupBean.getName());
                }

                groupName.setBackgroundColor(TitleColors[quickHolder.getAdapterPosition() % 10]);
                groupName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rv.setVisibility(rv.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                    }
                });
                //需要重置更新后的数据，否则查看不了
                groupBean.resetAccountList();
                rv.setLayoutManager(new LinearLayoutManager(mContext));
                QuickAdapter adapter = new QuickAdapter<AccountBean>(R.layout.recycler_account_details, groupBean.getAccountList()) {
                    @Override
                    public void _convert(QuickHolder quickHolder, final AccountBean accountBean) {
                        TextView accName = quickHolder.getView(R.id.tv_account_name);
                        TextView accPwd = quickHolder.getView(R.id.tv_account_password);
                        TextView accMsg = quickHolder.getView(R.id.tv_account_message);
                        //登录账号数值大于组等级 或 账号等级时 不显示
                        if (APP.getInstance().mLoginType > groupBean.getPasswordType() || APP.getInstance().mLoginType > accountBean.getPasswordType()) {
                            accName.setText("账号：" + "无权限");
                            accPwd.setText("密码：" + "无权限");
                            accMsg.setText("说明：" + "无权限");
                        } else {
                            accName.setText("账号：" + new String(RsaUtils.decryptByPublicKeyForSpilt(accountBean.getAccountName(), APP.getInstance().mUserBean.getRsaPublicKey())));
                            accPwd.setText("密码：" + new String(RsaUtils.decryptByPublicKeyForSpilt(accountBean.getAccountPwd(), APP.getInstance().mUserBean.getRsaPublicKey())));
                            accMsg.setText("说明：" + accountBean.getAccountMsg());
                        }

                        ImageView update = quickHolder.getView(R.id.iv_update);
                        ImageView delete = quickHolder.getView(R.id.iv_delete);
                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //更新修改数据
                            }
                        });
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //删除数据
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("删除账号")
                                        .setMessage("账号删除后将不可恢复！")
                                        .setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mAccountBeanDao.delete(accountBean);
                                                mQuickAdapter.notifyDataSetChanged();
                                            }
                                        })
                                        .setNegativeButton("取消", null)
                                        .create().show();
                            }
                        });

                    }
                };
                rv.setAdapter(adapter);
            }
        };
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mQuickAdapter);
    }

    private void getAccountList() {

        List<GroupBean> lists = APP.getInstance().getDaoSession().getGroupBeanDao().queryBuilder()
                .where(GroupBeanDao.Properties.UserId.eq(APP.getInstance().mUserBean.getId()))
                .build().list();
        mSwipe.setRefreshing(false);
        mGroupBeanList.clear();
        mGroupBeanList.addAll(lists);
        mQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int i, Intent intent) {

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
                                mSwipe.setRefreshing(true);
                                getAccountList();
                            }
                        })
                        .show();
                break;
        }
    }
}
