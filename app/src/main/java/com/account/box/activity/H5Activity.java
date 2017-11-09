package com.account.box.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.widget.Toolbar;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.account.box.APP;
import com.account.box.R;
import com.account.box.utils.ToolUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jjs.base.base.BaseH5Activity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;

/**
 * 说明：
 * Created by Administrator on 2017/9/29.
 */

public class H5Activity extends BaseH5Activity {
    @BindView(R.id.tool)
    Toolbar mTool;
    @BindView(R.id.webview)
    WebView mWebView;

    @StringDef({About, Skill})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TagForHtmlUrl {
    }

    public static final String About = "file:///android_asset/About.html";
    public static final String Skill = "file:///android_asset/Skill.html";

    public static void open(Context context, @TagForHtmlUrl String tag) {
        Intent intent = new Intent(context, H5Activity.class);
        intent.putExtra("tag", tag);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ToolUtils.initTool(this, mTool, "", true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String url = getIntent().getStringExtra("tag");
        init(mWebView, url);
        switch (url) {
            case About:
                //设置js支持
                mWebView.getSettings().setJavaScriptEnabled(true);
                //设置H5调用java支持：h5通过js   window.android.copy 进行调用java代码
                mWebView.addJavascriptInterface(new JsInterface(), "android");
                break;
            case Skill:

                break;
            default:
                ToastUtils.showShort("tag is not equals");
                break;
        }
    }

    public class JsInterface {
        //for About
        @JavascriptInterface
        public void copy() {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData myClip;
            myClip = ClipData.newPlainText("text", APP.getInstance().apkUrl);//text是内容
            clipboardManager.setPrimaryClip(myClip);
            ToastUtils.showShort("复制成功");
        }
    }

    @Override
    protected void _onReceivedTitle(WebView webView, String s) {
        if (mTool != null) {
            mTool.setSubtitle(s);
        }
    }

    @Override
    protected void _onProgressChanged(WebView webView, int i) {

    }

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }
}
