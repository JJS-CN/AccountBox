package com.account.box;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.account.box.bean.UserBean;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.jjs.base.MultiDexAPP;
import com.jjs.base.utils.UEHandler;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * 说明：
 * Created by aa on 2017/8/8.
 */

public class APP extends MultiDexAPP {
    //APK的下载地址，使用github的地址
    public String apkUrl = "https://raw.githubusercontent.com/JJS-CN/AccountBox/master/app/AccountBox.apk";
    //自定义数据库保存位置
    private String sdPath = SDCardUtils.getSDCardPath() + File.separator + "accountBox";

    private static APP mApplication;
    public UserBean mUserBean;//用户数据
    public String passwordSuper = "jjs";//超级密码
    public int mLoginType = Store.Password.Public;//登录类型

    @Override
    public void onCreate() {
        mApplication = this;
        //是否打开log开关
        Store.HTTP.URL_release = "http://119.29.238.157/api/";
        Store.HTTP.URL_debug = "http://119.29.238.157/api/";
        isDebug=true;
        //需要在super之前调用设置
        super.onCreate();
        if (!isDebug) {
            //新版只需调用init即可
            UEHandler.init(this, LauncherActivity.class);
        }
        SophixManager.getInstance().queryAndLoadNewPatch();
        initActLifecycle();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        isDebug = false;//重要开关，关系到异常捕获、log信息、热修复等功能的使用
        SophixManager.getInstance()
                .setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setSecretMetaData("24643061-1", "331fb7e469d6445b11b041d3c2f6bb89", "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDRi5foTHsLSYG02535sExdyqaeI88gluu/CWuzMwlfXuh1RwmtJxFR2R34YztRPb+6QpOte8/WleyPbFUQsLxVEB4EqX+APVhC2Wma3J9/qOioLD+p56PAYfFwns3YqGx+4g7IXQmfJ4W7WRfJiK2WpJt3MfsiW07veiUP3RhoI0x9XOpwZFXpleqbPhFkQuGW0zz81tdvierdv3xz0Nf3wOCTWdulnCpJ3o1witjzrjiCL2qxM+N/YSyXy43pQjHvIMOpokHm+pLP0cJckoz7PraZt+B2O2zReT692TXqVnEpT/MMfQDFo5zo8fxMEyQ9PfBqWtHyfBpZelz82on3AgMBAAECggEBAMR2Ma2Nj0ts1pw/0CRbA65oIsFf54GxnkQVTBHORvpQ3Hega9UZLOblPnE0cFfiIhE6/ISJ8bpKBGoP8s1cNqc2Hj7WwAD3AvpiJt4X/b7OGZwbKHFfCauE6L5jVbHNbzOjNKLsf+nlP0AA7wl4CPCVUmF1zfRPUXm7tmXiMFDpG/i8S6JdbkDMtFzFnjHpXCOmvMc3259mQICCzkwxQM3bAxpqyv0YUQ8Lk9mQp+8vVVFhSqrpRFSrNmdEAjpD/77QIxPCaqVcfoywInsI1DmPXa2Q1JGVdcTdC3msYEK3sy2dTiB5rkSsEghBBtj5hpc+7V9wBAXC78Ab7zEu7BkCgYEA+Y/Py54QpTvfQOA6j4ufOB2K0lYef/unlJDZIgvpuh2v4k486pX/bUKzxezJhYfapekB4OUFVzWpAZ/bupbO81FPRAqkeGpx+kGGh0GU87Ad6ytdsew7h1MHChPbUCnGaVpNzHSFB9FgJaKexISY0FTu8Ix0eNgljb5q7CinfGMCgYEA1vN/6V54Kv4eog7ulHNrlDAdRcIz4/tZP6lbkLOS0b68bOA3sBInBshxzq4e1kYR8MNBWER2x1M5hhAyFJ2Y52uRmqtWmmdZ9EoODlVXyUVyaoscPY2C/h6nmZcfhA47QScUESm3Rk8TWJevU55c62IyTrzvJ1ytvoKBWu8yXl0CgYBwuB0SCf3Ys/c9lUm+BkFPFkDaih1Sjzb86oO4VgsmjjwgCliHWGK7VfJfBnlrP5Zpc5num2LTJiCE5FBZsb4dtm8H+4qX/PQoJap+swd1HeoVi0vRjjLBB6ZZTei0B3pfoXMcUtC3XEGCHjZ56q4fAw29RqNg5bfoksY2jpI/owKBgC2H4p5SLm4c6ew18X9se2smTe0K1+cAxMThYM3j28Ol/U+qpuEa1z1uk/zNkSaeLQqssVi7F9Xc4SBuqc0IHO1YMb1rcaq/HaTLUvLiTPgLR9MdqFu5b/fYEnRMOJbGVPLWDkAcb//qnBR1/ONfdhs9754SEy5pSTxDdJcId8jZAoGBAJqeGBeiBDWJX+MMh284Q+H+tALMhh0lGsYa3PosS/w5cBAGGc+VnegnscoCeF+Bk3bWzqGL2fYZk0X001hXuJX7VXqbSRgsQLrwk/P/oo63osdzCfr3YM6ltOu6FWYIPntgFN9W1CrnU/QvUR+RGs0sG6LVVvwkb37N4OEC5FnK")
                .setAesKey(null)
                .setEnableDebug(isDebug)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Log.e("热修复", "接收到热修复通知：mode---" + mode + "  code---" + code + "  info---" + info + "  handlePatchVersion--" + handlePatchVersion);
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            //Log.e("热修复","热修复补丁加载成功");
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            //Log.e("热修复","热修复新补丁生效需要重启,提示用户重启或强制kill");
                            SophixManager.getInstance().killProcessSafely();//官方建议调用此方法进行强制重启，否则会扰乱框架的正常运行
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            //Log.e("热修复","热修复发生错误！");
                        }
                    }
                }).initialize();
      /*  mode: 补丁模式, 0:正常请求模式 1:扫码模式 2:本地补丁模式
        code: 补丁加载状态码, 详情查看PatchStatusCode类说明
        info: 补丁加载详细说明, 详情查看PatchStatusCode类说明
        handlePatchVersion: 当前处理的补丁版本号, 0:无 -1:本地补丁 其它:后台补丁*/
       /* code: 1 补丁加载成功
        code: 6 服务端没有最新可用的补丁
        code: 11 RSASECRET错误，官网中的密钥是否正确请检查
        code: 12 当前应用已经存在一个旧补丁, 应用重启尝试加载新补丁
        code: 13 补丁加载失败, 导致的原因很多种, 比如UnsatisfiedLinkError等异常, 此时应该严格检查logcat异常日志
        code: 16 APPSECRET错误，官网中的密钥是否正确请检查
        code: 18 一键清除补丁
        code: 19 连续两次queryAndLoadNewPatch()方法调用不能短于3s*/
    }

    /**
     * 获取具体实例
     */
    public static APP getInstance() {
        return mApplication;
    }

    /**
     * 获取头像路径地址
     */
    public File getAvatarFile() {
        File avatarFile = new File(SDCardUtils.getSDCardPath() + "/AccountBox/AvatarCache");
        if (!avatarFile.exists()) {
            boolean result = avatarFile.mkdirs();
            LogUtils.e("创建头像目录：" + result);
        }
        return avatarFile;
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SophixManager.getInstance().killProcessSafely();
        }
    };

    /**
     * 大于 0说明应用在前台
     */
    public int mFinalCount = 0;

    /**
     * 监听应用是否在前台
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void initActLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                mFinalCount++;
                //如果mFinalCount ==1，说明是从后台到前台
                LogUtils.e("onActivityStarted--->" + mFinalCount + "");
                if (mFinalCount == 1) {
                    //说明从后台回到了前台
                    mHandler.removeCallbacksAndMessages(null);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                mFinalCount--;
                //如果mFinalCount ==0，说明是前台到后台
                LogUtils.e("onActivityStopped--->" + mFinalCount + "");
                if (mFinalCount == 0) {
                    //说明从前台回到了后台
                    mHandler.sendEmptyMessageDelayed(0, 120000);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });


    }
}
