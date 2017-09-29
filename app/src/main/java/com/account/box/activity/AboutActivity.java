package com.account.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.account.box.R;
import com.jjs.base.JJsActivity;

/**
 * 说明：
 * Created by Administrator on 2017/9/29.
 */

public class AboutActivity extends JJsActivity {
    public static void open(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }
}
