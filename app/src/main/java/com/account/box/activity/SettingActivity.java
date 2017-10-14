package com.account.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.account.box.R;
import com.jjs.base.JJsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 说明：
 * Created by aa on 2017/10/14.
 */

public class SettingActivity extends JJsActivity {
    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.rv_setting)
    RecyclerView mRvSetting;



    public static void open(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mTool.setSubtitle("设置");
        setSupportActionBar(mTool);
        mTool.setNavigationIcon(R.drawable.ic_back);
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }
}
