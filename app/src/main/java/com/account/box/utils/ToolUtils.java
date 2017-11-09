package com.account.box.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.account.box.R;

import org.greenrobot.greendao.annotation.NotNull;

/**
 * 说明：
 * Created by aa on 2017/10/14.
 */

public class ToolUtils {
    public static void initTool(final AppCompatActivity activity, @NotNull Toolbar toolbar, @NotNull String title, boolean hasBack) {
        toolbar.setSubtitle(title);
        activity.setSupportActionBar(toolbar);
        if (hasBack) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
    }
}
