package com.account.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.account.box.R;
import com.account.box.utils.ToolUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jjs.base.base.BaseActivity;
import com.jjs.base.utils.recyclerview.QuickAdapter;
import com.jjs.base.utils.recyclerview.QuickHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 说明：
 * Created by aa on 2017/10/14.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.rv_setting)
    RecyclerView mRvSetting;

    List<String> settList;
    QuickAdapter mQuickAdapter;

    public static void open(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        ToolUtils.initTool(this, mTool, "设置", true);
        initRecyclerView();
    }

    private void initRecyclerView() {
        settList = new ArrayList<>();
        settList.add("修改密码");
        mQuickAdapter = new QuickAdapter<String>(R.layout.recycler_setting, settList) {
            @Override
            public void _convert(QuickHolder quickHolder, String s) {
                quickHolder.setText(R.id.tv_name, s);
            }
        };
        mQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (settList.get(position)) {
                    case "修改密码":
                        ResetPwdActivity.open(SettingActivity.this);
                        break;
                }
            }
        });
        mRvSetting.setLayoutManager(new LinearLayoutManager(this));
        mRvSetting.setAdapter(mQuickAdapter);
    }

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }
}
