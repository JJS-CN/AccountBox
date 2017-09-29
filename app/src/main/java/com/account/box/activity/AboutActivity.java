package com.account.box.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.account.box.APP;
import com.account.box.R;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.JJsActivity;

import butterknife.BindView;

/**
 * 说明：
 * Created by Administrator on 2017/9/29.
 */

public class AboutActivity extends JJsActivity {
    @BindView(R.id.ll_copy)
    LinearLayout mLlCopy;

    public static void open(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mLlCopy.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", APP.getInstance().apkUrl);//text是内容
                clipboardManager.setPrimaryClip(myClip);
                ToastUtils.showShort("复制成功");
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }
}
