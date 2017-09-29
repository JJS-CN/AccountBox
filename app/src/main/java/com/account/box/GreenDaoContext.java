package com.account.box;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SDCardUtils;

import java.io.File;
import java.io.IOException;

/**
 * 说明：用于Greendao自定义数据库保存路径
 * Created by aa on 2017/9/29.
 */

public class GreenDaoContext extends ContextWrapper {

    private String sdPath = "";//数据库路径，为空时默认选用data路径

    public GreenDaoContext(Context context) {
        super(context);
    }

    /**
     * 传入数据库自定义路径，结尾不含分隔符
     * 将与dbName拼接
     */
    public GreenDaoContext(Context context,String sdPath) {
        super(context);
        this.sdPath = sdPath;
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象
     *
     * @param dbName
     */
    @Override
    public File getDatabasePath(String dbName) {
        //进行sd卡路径的空判断
        if (TextUtils.isEmpty(sdPath)) {
            sdPath = SDCardUtils.getDataPath();
        }
        if (TextUtils.isEmpty(sdPath)) {
            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append(sdPath);
        buffer.append(File.separator);//分隔符，因为window和liux系统分隔符不同，尽量由此代替
        buffer.append(dbName);
        String dbPath = buffer.toString();// 数据库路径

        // 判断目录是否存在，不存在则创建该目录
        File dirFile = new File(sdPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        // 数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        // 判断文件是否存在，不存在则创建该文件
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();// 创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            isFileCreateSuccess = true;
        // 返回数据库文件对象
        if (isFileCreateSuccess)
            return dbFile;
        else
            return super.getDatabasePath(dbName);
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String, int,
     * android.database.sqlite.SQLiteDatabase.CursorFactory,
     * android.database.DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory,
                                               DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);

        return result;
    }

}
