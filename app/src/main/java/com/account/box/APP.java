package com.account.box;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.jjs.base.MultiDexAPP;
import com.jjs.base.utils.UEHandler;

import java.io.File;

/**
 * 说明：
 * Created by aa on 2017/8/8.
 */

public class APP extends MultiDexAPP {


    @Override
    public void onCreate() {

        //是否打开log开关
        Store.HTTP.URL_release = "";
        Store.HTTP.URL_debug = "";
        isDebug = true;
        //需要在super之前调用设置
        super.onCreate();
        if (!isDebug) {
            //新版只需调用init即可
            UEHandler.init(this, LauncherActivity.class);
        }
    }

    public static File getAvatarFile() {
        File avatarFile = new File(SDCardUtils.getSDCardPath() + "/AccountBox/AvatarCache");
        if (!avatarFile.exists()) {
            boolean result = avatarFile.mkdirs();
            LogUtils.e("创建头像目录：" + result);
        }
        return avatarFile;
    }
}
