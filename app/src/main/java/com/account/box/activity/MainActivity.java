package com.account.box.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.account.box.R;
import com.jjs.base.JJsActivity;

import java.util.List;

public class MainActivity extends JJsActivity {
    public static void open(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreateView(@Nullable Bundle bundle) {
        setContentView(R.layout.activity_main);
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


}
