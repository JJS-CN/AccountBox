package com.account.box.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.account.box.APP;
import com.account.box.R;
import com.account.box.Store;
import com.account.box.bean.AccountBean;
import com.account.box.bean.AccountBeanDao;
import com.account.box.bean.GroupBean;
import com.account.box.bean.RxResult;
import com.account.box.http.ApiService;
import com.account.box.http.RxObserver;
import com.account.box.utils.AddDialog;
import com.account.box.utils.GlideApp;
import com.account.box.utils.StatusBarUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jjs.base.base.BaseActivity;
import com.jjs.base.http.RetrofitUtils;
import com.jjs.base.http.RxSchedulers;
import com.jjs.base.utils.GlideUtils;
import com.jjs.base.utils.recyclerview.QuickAdapter;
import com.jjs.base.utils.recyclerview.QuickHolder;
import com.jjs.base.widget.BaseDialogFragment;
import com.rodolfonavalon.shaperipplelibrary.ShapeRipple;

import java.io.File;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class MainActivity extends BaseActivity {
    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;

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

    @BindView(R.id.ll_top)
    LinearLayout ll_top;

    ImageView mIvHeadBg, mIvHeadAvatar;
    TextView mTvUserName, mTvGroupCount, mTvAccountCount, mTvVersion;
    private int rippleTime = 1500;//????????????????????????

    int[] TitleColors = new int[]{
            Color.parseColor("#FFF5EE"), Color.parseColor("#FFDAB9"),
            Color.parseColor("#FFBBFF"), Color.parseColor("#BFEFFF"),
            Color.parseColor("#B9D3EE"), Color.parseColor("#9BCD9B"),
            Color.parseColor("#98FB98"), Color.parseColor("#C0FF3E"),
            Color.parseColor("#9AFF9A"), Color.parseColor("#9F79EE")};
    //????????????
    QuickAdapter mQuickAdapter;
    InnerRecevier innerReceiver;

    List<GroupBean> mGroupBeanList;

    public static void open(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void openPair(Activity context, ImageView avatarView) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Pair pair = Pair.create(avatarView, "avatar");
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(context, pair).toBundle();

        ActivityCompat.startActivity(context, intent, bundle);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtils.setColor(this, Color.TRANSPARENT);
        ViewCompat.setTransitionName(mIvAvatar, "avatar");
        //????????????????????????
        mGroupBeanList = APP.getInstance().mUserBean.getGroups();
        //?????????view??????????????????
        initToolBar();
        initRecycler();
        initNavigation();
        initJPush();

        updateAccountList();//??????????????????

        hasExitDouble();//??????????????????

        mRipple.setEnableRandomColor(true);
        mRipple.setEnableRandomPosition(true);

        //????????????
        innerReceiver = new InnerRecevier();
        //??????????????????
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //????????????
        registerReceiver(innerReceiver, intentFilter);

    }

    private void initJPush() {
        Log.e("alias", APP.getInstance().mUserBean.getUser().getId());
        if (JPushInterface.isPushStopped(getApplicationContext())) {
            JPushInterface.resumePush(getApplicationContext());
        }
        JPushInterface.setAlias(this, 1, APP.getInstance().mUserBean.getUser().getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(innerReceiver);
    }

    private void initToolBar() {
        //??????toolbar
        mTool.setSubtitle("????????????");
        setSupportActionBar(mTool);
        mTool.setNavigationIcon(R.drawable.nulls);
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????????????????????????????????????????????????????????????????
                mDrawer.openDrawer(Gravity.LEFT);
            }
        });

    }

    private void initRecycler() {
        //??????????????????
        mQuickAdapter = new QuickAdapter<GroupBean>(R.layout.recycler_group_show, mGroupBeanList) {
            @Override
            public void _convert(final QuickHolder quickHolder, final GroupBean groupBean) {
                //???????????????????????????????????????????????????
                CardView cardGroupName = quickHolder.getView(R.id.card_group_name);//???bg
                TextView groupName = quickHolder.getView(R.id.tv_group_name);//??????
                final TextView accountCount = quickHolder.getView(R.id.tv_accountCount);//????????????
                TextView shareName = quickHolder.getView(R.id.tv_share_name);//???????????????
                final RecyclerView rv = quickHolder.getView(R.id.rv_account_list);//????????????
                View iv_delete = quickHolder.getView(R.id.iv_delete);//?????????
                final View iv_more = quickHolder.getView(R.id.iv_more);//???????????????
                View iv_share = quickHolder.getView(R.id.iv_share);//?????????
                View iv_share_delete = quickHolder.getView(R.id.iv_not_share);//????????????


                groupName.setText(groupBean.getGroup_name());
                if (groupBean.getAccounts() == null || groupBean.getAccounts().size() == 0) {
                    iv_delete.setVisibility(View.VISIBLE);
                    iv_more.setVisibility(View.INVISIBLE);
                    accountCount.setText("");
                } else {
                    iv_delete.setVisibility(View.INVISIBLE);
                    iv_more.setVisibility(View.VISIBLE);
                    accountCount.setText("->" + groupBean.getAccounts().size());
                }
                if (groupBean.getOwner_id().equals(APP.getInstance().mUserBean.getUser().getId())) {
                    iv_share.setVisibility(View.VISIBLE);
                    shareName.setVisibility(View.GONE);
                    iv_share_delete.setVisibility(View.INVISIBLE);
                } else {
                    iv_share.setVisibility(View.INVISIBLE);
                    iv_delete.setVisibility(View.INVISIBLE);
                    shareName.setVisibility(View.VISIBLE);
                    iv_share_delete.setVisibility(View.VISIBLE);
                    shareName.setText("(???????????????" + groupBean.getOwner_id() + ")");
                }
                //??????list????????????????????????????????????
                if (groupBean.isOpen()) {
                    rv.setVisibility(View.VISIBLE);
                    iv_more.setRotation(90);
                } else {
                    rv.setVisibility(View.GONE);
                    iv_more.setRotation(0);
                }
                //????????? ??????list??????
                cardGroupName.setBackgroundColor(TitleColors[quickHolder.getAdapterPosition() % 10]);
                cardGroupName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        groupBean.setOpen(!groupBean.isOpen());
                        rv.setVisibility(groupBean.isOpen() ? View.VISIBLE : View.GONE);
                        //????????????????????????
                        if (iv_more.getVisibility() == View.VISIBLE) {
                            final ValueAnimator animator;

                            if (groupBean.isOpen()) {
                                animator = ValueAnimator.ofFloat(0, 90);
                            } else {
                                animator = ValueAnimator.ofFloat(90, 0);
                            }
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    iv_more.setRotation((Float) animation.getAnimatedValue());
                                }
                            });
                            animator.setDuration(150);
                            animator.setInterpolator(new AccelerateDecelerateInterpolator());
                            animator.start();
                        }
                        mRv.scrollToPosition(quickHolder.getAdapterPosition());
                    }
                });
                //??????
                iv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseDialogFragment.getInstance(R.layout.dialog_share)
                                .hasCancelable(true)
                                .setInitViewListener(new BaseDialogFragment.OnInitViewListener() {
                                    @Override
                                    public void onInit(BaseDialogFragment.ViewHolder viewHolder) {
                                        viewHolder.setClick(R.id.btn_cancel);
                                        viewHolder.setClick(R.id.btn_check);
                                    }
                                })
                                .setBaseClickListener(new BaseDialogFragment.OnBaseClickListener() {
                                    @Override
                                    public void onClick(final BaseDialogFragment.ViewHolder viewHolder, View view) {
                                        switch (view.getId()) {
                                            case R.id.btn_check:
                                                TextInputEditText editText = viewHolder.getView(R.id.edit_shareName);
                                                String shareName = editText.getText().toString().trim();
                                                if (TextUtils.isEmpty(shareName)) {
                                                    ToastUtils.showShort("?????????????????????");
                                                }
                                                RetrofitUtils.getInstance().create(ApiService.Message.class)
                                                        .inviteUserJoinGroup(APP.getInstance().mUserBean.getUser().getId(), shareName, groupBean.getId())
                                                        .compose(RxSchedulers.getInstance(MainActivity.this.bindToLifecycle()).<RxResult<String>>io_main())
                                                        .subscribe(new RxObserver<String>() {
                                                            @Override
                                                            protected void _onSuccess(String s) {
                                                                ToastUtils.showShort(s);
                                                                viewHolder.dismiss();
                                                            }
                                                        });
                                                break;
                                            case R.id.btn_cancel:
                                                viewHolder.dismiss();
                                                break;
                                        }
                                    }
                                })
                                .show(getFragmentManager(), "share");
                    }
                });
                //????????? ???????????????????????????
                iv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RetrofitUtils.getInstance().create(ApiService.Account.class)
                                .deleteGroup(APP.getInstance().mUserBean.getUser().getId(), groupBean.getId())
                                .compose(RxSchedulers.getInstance(MainActivity.this.bindToLifecycle()).<RxResult<String>>io_main())
                                .subscribe(new RxObserver<String>() {
                                    @Override
                                    protected void _onSuccess(String s) {
                                        ToastUtils.showShort(s);
                                        updateAccountList();
                                    }
                                });
                    }
                });
                //????????????
                iv_share_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RetrofitUtils.getInstance().create(ApiService.Account.class)
                                .exitGroup(APP.getInstance().mUserBean.getUser().getId(), groupBean.getId())
                                .compose(RxSchedulers.getInstance(MainActivity.this.bindToLifecycle()).<RxResult<String>>io_main())
                                .subscribe(new RxObserver<String>() {
                                    @Override
                                    protected void _onSuccess(String s) {
                                        ToastUtils.showShort(s);
                                        updateAccountList();
                                    }
                                });
                    }
                });

                rv.setLayoutManager(new LinearLayoutManager(mContext));
                QuickAdapter adapter = new QuickAdapter<AccountBean>(R.layout.recycler_account_details, groupBean.getAccounts()) {
                    @Override
                    public void _convert(QuickHolder quickHolder, final AccountBean accountBean) {
                        //????????????????????????????????? ??? ??????????????? ?????????
                        quickHolder.setText(R.id.tv_account_title, accountBean.getTitle());
                        quickHolder.setText(R.id.tv_account_name, "?????????" + accountBean.getAccount_name());
                        quickHolder.setText(R.id.tv_account_password, "?????????" + accountBean.getPassword());
                        if (TextUtils.isEmpty(accountBean.getRemark())) {
                            quickHolder.setText(R.id.read_account_message, "");
                        } else {
                            quickHolder.setText(R.id.read_account_message, "??????:" + accountBean.getRemark());
                        }

                        quickHolder.addOnClickListener(R.id.iv_update);
                        quickHolder.addOnClickListener(R.id.iv_delete);
                        quickHolder.addOnClickListener(R.id.iv_upload);
                        quickHolder.setVisible(R.id.iv_upload, accountBean.isSql());
                        //????????????????????????
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            quickHolder.itemView.setZ(-quickHolder.getAdapterPosition() * 4);
                            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) quickHolder.itemView.getLayoutParams();
                            params.setMargins(ConvertUtils.dp2px((5 - Math.abs(quickHolder.getAdapterPosition() % 10 - 4)) * 3),
                                    ConvertUtils.dp2px(quickHolder.getAdapterPosition() == 0 ? -10 : -15), 0, 0);
                            quickHolder.itemView.setLayoutParams(params);
                        } else {
                            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) quickHolder.itemView.getLayoutParams();
                            params.setMargins(ConvertUtils.dp2px((5 - Math.abs(quickHolder.getAdapterPosition() % 10 - 4)) * 3), 0, 0, 0);
                            quickHolder.itemView.setLayoutParams(params);
                        }

                    }
                };
                adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                        final AccountBean accountBean = groupBean.getAccounts().get(position);
                        switch (view.getId()) {
                            case R.id.iv_update:
                                OpenDialog(accountBean);
                                break;
                            case R.id.iv_delete:
                                //????????????
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("????????????")
                                        .setMessage("?????????????????????????????????")
                                        .setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (!accountBean.isSql()) {
                                                    RetrofitUtils.getInstance()
                                                            .create(ApiService.Account.class)
                                                            .deleteAccount(APP.getInstance().mUserBean.getUser().getId(), accountBean.getId())
                                                            .compose(RxSchedulers.getInstance(MainActivity.this.bindToLifecycle()).<RxResult<String>>io_main())
                                                            .subscribe(new RxObserver<String>() {
                                                                @Override
                                                                protected void _onSuccess(String s) {
                                                                    updateAccountList();
                                                                }
                                                            });
                                                } else {
                                                    APP.getDaoInstant().getAccountBeanDao().delete(accountBean);
                                                    ToastUtils.showShort("????????????");
                                                    updateAccountList();
                                                }
                                            }
                                        })
                                        .setNegativeButton("??????", null)
                                        .create().show();
                                break;
                            case R.id.iv_upload:
                                //????????????
                                RetrofitUtils.getInstance().create(ApiService.Account.class)
                                        .addAccountToGroup(APP.getInstance().mUserBean.getUser().getId(),accountBean.getGroup_id() , accountBean.getTitle(), accountBean.getAccount_name(), accountBean.getPassword(), accountBean.getRemark())
                                        .compose(RxSchedulers.getInstance(MainActivity.this.bindToLifecycle()).<RxResult<String>>io_main())
                                        .subscribe(new RxObserver<String>() {
                                            @Override
                                            protected void _onSuccess(String msg) {
                                                ToastUtils.showShort("???????????????");
                                                updateAccountList();
                                            }
                                        });
                                break;
                        }
                    }
                });
                rv.setAdapter(adapter);
            }
        };
        mQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mQuickAdapter);
    }

    private void initNavigation() {
        View headView = mNavigationLeft.getHeaderView(0);//????????????
        mIvHeadBg = (ImageView) headView.findViewById(R.id.iv_bg);
        mIvHeadAvatar = (ImageView) headView.findViewById(R.id.iv_avatar);
        mTvUserName = (TextView) headView.findViewById(R.id.tv_username);
        mTvGroupCount = (TextView) headView.findViewById(R.id.tv_groupCount);
        mTvAccountCount = (TextView) headView.findViewById(R.id.tv_accountCount);
        mTvVersion = (TextView) headView.findViewById(R.id.tv_version);

        mTvVersion.setText("Version???" + AppUtils.getAppVersionName());
        mIvHeadBg.setImageBitmap(ImageUtils.fastBlur(BitmapFactory.decodeResource(getResources(), R.drawable.c1), 0.2f, 25));
        mTvUserName.setText(APP.getInstance().mUserBean.getUser().getAccount());
        //??????
        String headPath = APP.getInstance().mUserBean.getUser().getHead_path();
        if (!TextUtils.isEmpty(headPath)) {
            GlideApp.with(this).load(headPath).transform(new GlideUtils.CircleTransform()).error(R.drawable.main_default_avatar).into(mIvAvatar);
            GlideApp.with(this).load(headPath).transform(new GlideUtils.CircleTransform()).error(R.drawable.main_default_avatar).into(mIvHeadAvatar);
        }
        headView.findViewById(R.id.ll_skill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H5Activity.open(MainActivity.this, H5Activity.Skill);
            }
        });
        mIvHeadAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.requestPermissions(MainActivity.this, 0, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //????????????,?????????(?????????????????????????????????????????????????????????????????????????????????)
                       /* Intent intentCurp = new Intent(Intent.ACTION_PICK);
                        intentCurp.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                        intentCurp.putExtra("crop", "true");
                        intentCurp.putExtra("aspectX", 1);// aspectX???????????????????????????????????????????????????????????????1:1???
                        intentCurp.putExtra("aspectY", 1);
                        intentCurp.putExtra("outputX", 400); // outputX outputY ?????????????????????
                        intentCurp.putExtra("outputY", 400); //?????????????????????????????????????????????<640
                        intentCurp.putExtra("scale", true);
                        File avatarFile = new File(APP.getInstance().getAvatarFile(), APP.getInstance().mUserBean.getUsername() + ".jpg");
                        intentCurp.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(avatarFile));//??????????????????????????????
                        intentCurp.putExtra("return-data", false);
                        intentCurp.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                        //intentCurp.putExtra("noFaceDetection", true);
                        startActivityForResult(intentCurp, 0);*/
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 0);
                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {

                    }
                });

            }
        });

        mNavigationLeft.setItemIconTintList(null);//???????????????????????????
        mNavigationLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_message:
                        MessageListActivity.open(MainActivity.this);
                        break;
                    case R.id.navigation_item_setting:
                        SettingActivity.open(MainActivity.this);
                        break;
                    case R.id.navigation_item_about:
                        H5Activity.open(MainActivity.this, H5Activity.About);
                        break;
                    case R.id.navigation_item_loginOut:
                        SPUtils.getInstance().remove("password");
                        APP.getInstance().mUserBean = null;
                        JPushInterface.stopPush(getApplicationContext());
                        LoginActivity.open(MainActivity.this);
                        finish();
                        break;
                }
                mDrawer.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    /**
     * ??????????????????????????????
     */
    private void updateNavigation() {
        mTvGroupCount.setText("???:" + mGroupBeanList.size());
        int accountCount = 0;
        for (int i = 0; i < mGroupBeanList.size(); i++) {
            accountCount += mGroupBeanList.get(i).getAccounts().size();
        }
        mTvAccountCount.setText("??????:" + accountCount);
    }

    /**
     * ????????????????????????????????????????????????
     */
    private void updateAccountList() {
        mRipple.startRipple();
        RetrofitUtils.getInstance()
                .create(ApiService.Account.class)
                .getGroupListAll(APP.getInstance().mUserBean.getUser().getId())
                .compose(RxSchedulers.getInstance(this.bindToLifecycle()).<RxResult<List<GroupBean>>>io_main())
                .subscribe(new RxObserver<List<GroupBean>>() {
                    @Override
                    protected void _onSuccess(List<GroupBean> groupBeen) {
                        Collections.sort(groupBeen);
                        mGroupBeanList = groupBeen;
                        List<AccountBean> accountList = APP.getDaoInstant().getAccountBeanDao()
                                .queryBuilder()
                                .where(AccountBeanDao.Properties.UserId.eq(APP.getInstance().mUserBean.getUser().getId()))
                                .build().list();
                        //??????????????????????????????????????????????????????????????????????????????title???????????????????????????
                        /*for (int i = 0; i < groupBeen.size(); i++) {
                            if (groupBeen.get(i).getAccounts() != null && groupBeen.get(i).getAccounts().size() != 0) {
                                for (int j = 0; j < groupBeen.get(i).getAccounts().size(); j++) {
                                    AccountBean a1 = groupBeen.get(i).getAccounts().get(j);
                                    for (int k = 0; k < accountList.size(); k++) {
                                        AccountBean b1 = accountList.get(k);
                                        if (a1.getAccount_name().equals(b1.getAccount_name()) && a1.getPassword().equals(b1.getPassword())&&a1.get) {

                                        }
                                    }
                                }
                            }
                        }*/
                        //??????accountList???????????????null?????????????????????
                        for (int i = 0; i < groupBeen.size(); i++) {
                            for (int j = 0; j < groupBeen.get(i).getAccounts().size(); j++) {
                                AccountBean a1 = groupBeen.get(i).getAccounts().get(j);
                                for (int k = 0; k < accountList.size(); k++) {
                                    if (a1.getAccount_name().equals(accountList.get(k).getAccount_name())
                                            && a1.getPassword().equals(accountList.get(k).getPassword())
                                            && a1.getTitle().equals(accountList.get(k).getTitle())) {
                                        accountList.remove(k);
                                        break;
                                    }
                                }
                            }
                        }
                        //?????????????????????????????????????????????????????????????????????????????????
                        for (int i = 0; i < accountList.size(); i++) {
                            boolean isadd = false;
                            for (int j = 0; j < groupBeen.size(); j++) {
                                if (accountList.get(i).getGroup_id().equals(groupBeen.get(j).getId())) {
                                    //???????????????????????????
                                    isadd = true;
                                    mGroupBeanList.get(j).getAccounts().add(accountList.get(i));
                                    break;
                                }
                            }
                            if (isadd == false) {
                                //????????????????????????????????????
                                mGroupBeanList.get(0).getAccounts().add(accountList.get(i));
                            }
                        }
                        //????????????????????????????????????????????????????????????????????????????????????????????????
                        mQuickAdapter.setNewData(mGroupBeanList);
                        startRipple();
                        updateNavigation();
                    }
                });
    }

    /**
     * ??????????????????/????????????
     *
     * @param bean ???????????????
     */
    private void OpenDialog(AccountBean bean) {
        new AddDialog(this, bean)
                .setOnChangeListener(new AddDialog.OnChangeListner() {
                    @Override
                    public void onChange(int type) {
                        updateAccountList();
                    }
                })
                .show();
    }


    @Override
    public void onActivityResult(int i, Intent resultData) {
        if (i == 0) {
            Uri uri = resultData.getData();
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // crop=true?????????????????????Intent??????????????????VIEW?????????
            intent.putExtra("crop", "true");
            // ?????????
            intent.putExtra("scale", true);
            intent.putExtra("scaleUpIfNeeded", true);
            // aspectX aspectY ?????????????????????????????????????????????
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY ???????????????????????????
            intent.putExtra("outputX", 400);
            intent.putExtra("outputY", 400);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
            //????????????????????????
            intent.putExtra("noFaceDetection", true);
            //???????????????uri
            File avatarFile = new File(APP.getInstance().getAvatarFile(), "avatar.png");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(avatarFile));
            //????????????????????????
            intent.putExtra("return-data", false);
            startActivityForResult(intent, 1);
        }
        if (i == 1) {
            /**
             * Retrofit2.0 ????????????
             */
            final File avatarFile = new File(APP.getInstance().getAvatarFile(), "avatar.png");
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)//????????????
                    .addFormDataPart("userId", APP.getInstance().mUserBean.getUser().getId());//ParamKey.TOKEN ???????????????key????????????????????????
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), avatarFile);
            builder.addFormDataPart("userHead", avatarFile.getName(), imageBody);//imgfile ?????????????????????????????????
            List<MultipartBody.Part> parts = builder.build().parts();
            RetrofitUtils.getInstance().create(ApiService.User.class)
                    .uploadUserHead2(parts)
                    .compose(RxSchedulers.getInstance(this.bindToLifecycle()).<RxResult<String>>io_main())
                    .subscribe(new RxObserver<String>() {
                        @Override
                        protected void _onSuccess(String s) {
                            GlideApp.with(MainActivity.this).load(avatarFile).transform(new GlideUtils.CircleTransform()).error(R.drawable.main_default_avatar).into(mIvAvatar);
                            GlideApp.with(MainActivity.this).load(avatarFile).transform(new GlideUtils.CircleTransform()).error(R.drawable.main_default_avatar).into(mIvHeadAvatar);

                        }
                    });
        }
        if (i == Store.ResultCode.Message) {
            updateAccountList();
        }
    }


    @OnClick({R.id.iv_float})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_float:
                //????????????dialog
                OpenDialog(null);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ll_top.setAlpha(0);
    }

    class InnerRecevier extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";

        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        ll_top.setAlpha(1);
                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        ll_top.setAlpha(1);
                    }
                }
            }
        }
    }

    private void startRipple() {
        mRipple.startRipple();
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
}
