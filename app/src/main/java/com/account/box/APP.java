package com.account.box;

import com.account.box.bean.DaoMaster;
import com.account.box.bean.DaoSession;
import com.account.box.bean.UserBean;
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
    private static APP mApplication;
    public UserBean mUserBean;//用户数据

    @Override
    public void onCreate() {
        mApplication = this;
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

    /**
     * 获取GreeenDao3.2版本的dao层
     */
    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "accountBox.db", null);
            DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
