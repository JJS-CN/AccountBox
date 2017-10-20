package com.account.box.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.account.box.APP;
import com.account.box.R;
import com.account.box.Store;
import com.account.box.bean.MessageBean;
import com.account.box.bean.RxResult;
import com.account.box.http.ApiService;
import com.account.box.http.RxObserver;
import com.account.box.utils.ToolUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jjs.base.JJsActivity;
import com.jjs.base.http.RetrofitUtils;
import com.jjs.base.http.RxSchedulers;
import com.jjs.base.utils.recyclerview.QuickAdapter;
import com.jjs.base.utils.recyclerview.QuickHolder;
import com.jjs.base.widget.LabelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明：
 * Created by aa on 2017/10/14.
 */

public class MessageListActivity extends JJsActivity {

    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.ll_tab)
    LinearLayout mLlTab;
    @BindView(R.id.ll_send)
    LinearLayout mLlSend;
    @BindView(R.id.ll_receive)
    LinearLayout mLlReceive;
    @BindView(R.id.rv_message_list)
    RecyclerView mRvMessageList;


    List<MessageBean> mMessageList;
    QuickAdapter mQuickAdapter;
    int checkPostion = 0;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, MessageListActivity.class);
        activity.startActivityForResult(intent, Store.ResultCode.Message);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        ToolUtils.initTool(this, mTool, "消息", true);
        initMessageList();
    }

    private void initMessageList() {
        mMessageList = new ArrayList<>();
        mQuickAdapter = new QuickAdapter<MessageBean>(R.layout.recycler_message, mMessageList) {
            @Override
            public void _convert(QuickHolder quickHolder, MessageBean messageBean) {
                quickHolder.setText(R.id.tv_send, messageBean.getSend_user_id());
                quickHolder.setText(R.id.tv_time, messageBean.getTimeStr());
                quickHolder.setText(R.id.tv_message, messageBean.getContent());
                LabelView labelView = quickHolder.getView(R.id.label);
                switch (messageBean.getState()) {
                    case "0":
                        labelView.setText("待处理");
                        labelView.setBgColor(getResources().getColor(R.color.Gold));
                        break;
                    case "1":
                        labelView.setText("已同意");
                        labelView.setBgColor(getResources().getColor(R.color.Green));
                        break;
                    case "2":
                        labelView.setText("被拒绝");
                        labelView.setBgColor(getResources().getColor(R.color.Red));
                        break;
                }
            }
        };
        mQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                if (mMessageList.get(position).getState().equals("0") && APP.getInstance().mUserBean.getUser().getId().equals(mMessageList.get(position).getReceive_user_id())) {
                    //表示未读
                    new AlertDialog.Builder(MessageListActivity.this)
                            .setTitle("是否同意这个邀请")
                            .setMessage("用户：" + mMessageList.get(position).getSend_user_id() + "\n" + mMessageList.get(position).getContent())
                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    RetrofitUtils.getInstance().create(ApiService.Message.class)
                                            .acceptInvite(APP.getInstance().mUserBean.getUser().getId(), mMessageList.get(position).getId())
                                            .compose(RxSchedulers.getInstance(MessageListActivity.this.bindToLifecycle()).<RxResult<String>>io_main())
                                            .subscribe(new RxObserver<String>() {
                                                @Override
                                                protected void _onSuccess(String s) {
                                                    checkPosition();
                                                    setResult(Store.TAG.RESULT_OK);
                                                    dialog.dismiss();
                                                }
                                            });
                                }
                            })
                            .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    RetrofitUtils.getInstance().create(ApiService.Message.class)
                                            .rejectInvite(APP.getInstance().mUserBean.getUser().getId(), mMessageList.get(position).getId())
                                            .compose(RxSchedulers.getInstance(MessageListActivity.this.bindToLifecycle()).<RxResult<String>>io_main())
                                            .subscribe(new RxObserver<String>() {
                                                @Override
                                                protected void _onSuccess(String s) {
                                                    checkPosition();
                                                    dialog.dismiss();
                                                }
                                            });
                                }
                            }).create().show();
                    RetrofitUtils.getInstance().create(ApiService.Message.class)
                            .changeMsgState(APP.getInstance().mUserBean.getUser().getId(), mMessageList.get(position).getId())
                            .compose(RxSchedulers.getInstance(MessageListActivity.this.bindToLifecycle()).<RxResult<String>>io_main())
                            .subscribe(new RxObserver<String>() {
                                @Override
                                protected void _onSuccess(String s) {
                                    checkPosition();
                                }
                            });
                }
            }
        });
        mRvMessageList.setLayoutManager(new LinearLayoutManager(this));
        mRvMessageList.setAdapter(mQuickAdapter);
    }

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }

    @OnClick({R.id.ll_send, R.id.ll_receive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_send:
                checkPostion = 0;
                checkPosition();
                break;
            case R.id.ll_receive:
                checkPostion = 1;
                checkPosition();
                break;
        }
    }

    private void checkPosition() {
        for (int i = 0; i < mLlTab.getChildCount(); i++) {
            TextView textView = (TextView) ((LinearLayout) mLlTab.getChildAt(i)).getChildAt(0);
            View line = ((LinearLayout) mLlTab.getChildAt(i)).getChildAt(1);
            if (i == checkPostion) {
                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                line.setVisibility(View.VISIBLE);
            } else {
                textView.setTextColor(getResources().getColor(R.color.Text));
                line.setVisibility(View.INVISIBLE);
            }
        }
        switch (checkPostion) {
            case 0:
                RetrofitUtils.getInstance()
                        .create(ApiService.Message.class)
                        .getSendMessages(APP.getInstance().mUserBean.getUser().getId())
                        .compose(RxSchedulers.getInstance(this.bindToLifecycle()).<RxResult<List<MessageBean>>>io_main())
                        .subscribe(new RxObserver<List<MessageBean>>() {
                            @Override
                            protected void _onSuccess(List<MessageBean> messageBeen) {
                                mMessageList = messageBeen;
                                mQuickAdapter.setNewData(messageBeen);
                            }
                        });
                break;
            case 1:
                RetrofitUtils.getInstance()
                        .create(ApiService.Message.class)
                        .getReceiveMessages(APP.getInstance().mUserBean.getUser().getId())
                        .compose(RxSchedulers.getInstance(this.bindToLifecycle()).<RxResult<List<MessageBean>>>io_main())
                        .subscribe(new RxObserver<List<MessageBean>>() {
                            @Override
                            protected void _onSuccess(List<MessageBean> messageBeen) {
                                mMessageList = messageBeen;
                                mQuickAdapter.setNewData(messageBeen);
                            }
                        });
                break;
        }

    }


}
