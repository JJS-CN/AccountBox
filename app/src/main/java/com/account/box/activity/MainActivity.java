package com.account.box.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
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
import com.account.box.utils.StatusBarUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.JJsActivity;
import com.jjs.base.utils.recyclerview.QuickAdapter;
import com.jjs.base.utils.recyclerview.QuickHolder;
import com.jjs.base.widget.ReadMoreTextView;
import com.rodolfonavalon.shaperipplelibrary.ShapeRipple;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends JJsActivity {


    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;
    @BindView(R.id.navigation_left)
    NavigationView mNavigationLeft;
    @BindView(R.id.drawer)
    DrawerLayout mDrawer;

    //https://github.com/poldz123/ShapeRipple
    @BindView(R.id.ripple)
    ShapeRipple mRipple;
    @BindView(R.id.iv_float)
    FloatingActionButton mIvFloat;

    ImageView mIvHeadBg, mIvHeadAvatar;
    TextView mTvUserName, mTvGroupCount, mTvAccountCount, mTvVersion;
    private int rippleTime = 1500;//涟漪动画持续时间

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
        StatusBarUtils.setColor(this, Color.TRANSPARENT);
        //获取数据库操作层
        mGroupBeanDao = APP.getInstance().getDaoSession().getGroupBeanDao();
        mAccountBeanDao = APP.getInstance().getDaoSession().getAccountBeanDao();
        //执行各view的初始化操作
        initToolBar();
        initRecycler();
        initNavigation();

        getAccountList();//执行查询操作

        hasExitDouble();//需要连点退出

        mRipple.setEnableRandomColor(true);
        mRipple.setEnableRandomPosition(true);
        if (APP.getInstance().mLoginType != 0) {
            mIvFloat.hide();
        }
    }

    private void initToolBar() {
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
    }

    private void initRecycler() {
        //列表展示控件
        mQuickAdapter = new QuickAdapter<GroupBean>(R.layout.recycler_group_show, mGroupBeanList) {
            @Override
            public void _convert(QuickHolder quickHolder, final GroupBean groupBean) {
                //需要重置更新后的数据，否则查看不了
                groupBean.resetAccountList();

                final EditText groupName = quickHolder.getView(R.id.tv_group_name);
                final RecyclerView rv = quickHolder.getView(R.id.rv_account_list);
                View iv_delete = quickHolder.getView(R.id.iv_delete);
                final View iv_more = quickHolder.getView(R.id.iv_more);
                final View iv_cancel = quickHolder.getView(R.id.iv_cancel);
                final View iv_save = quickHolder.getView(R.id.iv_save);

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
                        //处理图片旋转动画
                        if (iv_more.getVisibility() == View.VISIBLE) {
                            RotateAnimation animation;
                            if (rv.getVisibility() == View.VISIBLE) {
                                animation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF,
                                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            } else {
                                animation = new RotateAnimation(90, 0, Animation.RELATIVE_TO_SELF,
                                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            }
                            animation.setDuration(150);
                            animation.setFillAfter(true);
                            animation.setInterpolator(new AccelerateDecelerateInterpolator());
                            iv_more.startAnimation(animation);
                        }

                    }
                });
                groupName.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (APP.getInstance().mLoginType > groupBean.getPasswordType()) {
                            ToastUtils.showShort("无权限");
                            return false;
                        }
                        groupName.setFocusable(true);
                        groupName.setFocusableInTouchMode(true);
                        iv_cancel.setVisibility(View.VISIBLE);
                        iv_save.setVisibility(View.VISIBLE);
                        return false;
                    }
                });
                //设置默认初始值
                if (groupBean.getAccountList() == null || groupBean.getAccountList().size() == 0) {
                    iv_delete.setVisibility(View.VISIBLE);
                    iv_more.setVisibility(View.INVISIBLE);
                } else {
                    iv_delete.setVisibility(View.INVISIBLE);
                    iv_more.setVisibility(View.VISIBLE);
                }
                if (iv_more.getVisibility() == View.VISIBLE) {
                    RotateAnimation animation;
                    if (rv.getVisibility() == View.VISIBLE) {
                        animation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF,
                                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    } else {
                        animation = new RotateAnimation(90, 0, Animation.RELATIVE_TO_SELF,
                                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    }
                    animation.setDuration(1);
                    animation.setFillAfter(true);
                    animation.setInterpolator(new AccelerateDecelerateInterpolator());
                    iv_more.startAnimation(animation);
                }

                //删除按钮
                iv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (APP.getInstance().mLoginType > groupBean.getPasswordType()) {
                            ToastUtils.showShort("无权限");
                            return;
                        }
                        mGroupBeanDao.delete(groupBean);
                        getAccountList();
                    }
                });

                //取消修改 组名
                iv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        groupName.setText(groupBean.getName());
                        groupName.setFocusable(false);
                        groupName.setFocusableInTouchMode(false);
                        iv_cancel.setVisibility(View.INVISIBLE);
                        iv_save.setVisibility(View.INVISIBLE);
                    }
                });
                //修改保存 组名
                iv_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(groupName.getText().toString())) {
                            ToastUtils.showShort("不能为空！");
                            return;
                        }
                        groupBean.setName(groupName.getText().toString());
                        mGroupBeanDao.update(groupBean);
                        groupName.setFocusable(false);
                        groupName.setFocusableInTouchMode(false);
                        iv_cancel.setVisibility(View.INVISIBLE);
                        iv_save.setVisibility(View.INVISIBLE);
                    }
                });


                rv.setLayoutManager(new LinearLayoutManager(mContext));
                QuickAdapter adapter = new QuickAdapter<AccountBean>(R.layout.recycler_account_details, groupBean.getAccountList()) {
                    @Override
                    public void _convert(QuickHolder quickHolder, final AccountBean accountBean) {
                        TextView accName = quickHolder.getView(R.id.tv_account_name);
                        TextView accPwd = quickHolder.getView(R.id.tv_account_password);
                        ReadMoreTextView accMsg = quickHolder.getView(R.id.read_account_message);
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
                                new AddDialog(MainActivity.this, accountBean)
                                        .setOnChangeListener(new AddDialog.OnChangeListner() {
                                            @Override
                                            public void onChange(int type) {
                                                getAccountList();
                                            }
                                        })
                                        .show();
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
                                                updateNavigation();
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

    private void initNavigation() {
        View headView = mNavigationLeft.getHeaderView(0);//获取头部
        mIvHeadBg = (ImageView) headView.findViewById(R.id.iv_bg);
        mIvHeadAvatar = (ImageView) headView.findViewById(R.id.iv_avatar);
        mTvUserName = (TextView) headView.findViewById(R.id.tv_username);
        mTvGroupCount = (TextView) headView.findViewById(R.id.tv_groupCount);
        mTvAccountCount = (TextView) headView.findViewById(R.id.tv_accountCount);
        mTvVersion = (TextView) headView.findViewById(R.id.tv_version);

        mTvVersion.setText("Version：" + AppUtils.getAppVersionName());
        mIvHeadBg.setImageBitmap(ImageUtils.fastBlur(BitmapFactory.decodeResource(getResources(), R.drawable.c1), 0.2f, 25));
        mTvUserName.setText(APP.getInstance().mUserBean.getUsername());

        mNavigationLeft.setItemIconTintList(null);//使图标显示本来颜色
        mNavigationLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_setting:
                        break;
                    case R.id.navigation_item_loginRecord:
                        break;
                    case R.id.navigation_item_password:
                        break;
                    case R.id.navigation_item_loginOut:
                        SPUtils.getInstance().remove("password");
                        LoginActivity.open(MainActivity.this);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    private void updateNavigation() {
        long groupCount = mGroupBeanDao.queryBuilder().where(GroupBeanDao.Properties.UserId.eq(APP.getInstance().mUserBean.getId())).count();
        long accountCount = mAccountBeanDao.queryBuilder().where(AccountBeanDao.Properties.UserId.eq(APP.getInstance().mUserBean.getId())).count();
        mTvGroupCount.setText("组:" + groupCount);
        mTvAccountCount.setText("账号:" + accountCount);
    }

    private void getAccountList() {
        mRipple.startRipple();
        List<GroupBean> lists = APP.getInstance().getDaoSession().getGroupBeanDao().queryBuilder()
                .where(GroupBeanDao.Properties.UserId.eq(APP.getInstance().mUserBean.getId()))
                .build().list();
        mGroupBeanList.clear();
        mGroupBeanList.addAll(lists);
        mQuickAdapter.notifyDataSetChanged();
        closeAmin();
        updateNavigation();
    }

    private void closeAmin() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(rippleTime);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRipple.stopRipple();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRipple.startAnimation(alphaAnimation);
    }

    @Override
    public void onActivityResult(int i, Intent intent) {

    }


    @OnClick({R.id.iv_float})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_float:
                //打开添加dialog
                new AddDialog(this, null)
                        .setOnChangeListener(new AddDialog.OnChangeListner() {
                            @Override
                            public void onChange(int type) {
                                getAccountList();
                            }
                        })
                        .show();
                break;
        }
    }
}
