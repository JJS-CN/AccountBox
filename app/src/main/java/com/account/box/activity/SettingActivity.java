package com.account.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.account.box.R;
import com.account.box.Store;
import com.account.box.utils.ToolUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
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
        settList.add("自动同步服务器");
        mQuickAdapter = new QuickAdapter<String>() {
            @Override
            public void initMultiType() {
                super.initMultiType();
                setMultiTypeDelegate(new MultiTypeDelegate<String>() {
                    @Override
                    protected int getItemType(String s) {
                        return "自动同步服务器".equals(s) ? 1 : 0;
                    }
                });
                getMultiTypeDelegate().registerItemType(1, R.layout.recycler_setting_switch)
                        .registerItemType(0, R.layout.recycler_setting_default);
            }

            @Override
            public void _convert(QuickHolder quickHolder, String s) {
                quickHolder.setText(R.id.tv_name, s);
                switch (s) {
                    case "自动同步服务器":
                        Switch aSwitch = quickHolder.getView(R.id.switch_change);
                        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                SPUtils.getInstance().put(Store.Setting.hasSynchronize, isChecked);
                            }
                        });
                        aSwitch.setChecked(SPUtils.getInstance().getBoolean(Store.Setting.hasSynchronize, true));
                        break;
                }
            }
        };
        mQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (settList.get(position)) {
                    case "修改密码":
                        ResetPwdActivity.open(SettingActivity.this);
                        break;
                    case "自动同步服务器":
                        break;
                }
            }
        });
        mQuickAdapter.setNewData(settList);
        mRvSetting.setLayoutManager(new LinearLayoutManager(this));
        mRvSetting.setAdapter(mQuickAdapter);
    }

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }
}
