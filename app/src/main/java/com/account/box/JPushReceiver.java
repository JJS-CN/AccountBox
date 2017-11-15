package com.account.box;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.account.box.activity.MessageListActivity;
import com.blankj.utilcode.util.LogUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 说明：
 * Created by aa on 2017/10/26.
 */

public class JPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        if (bundle==null) return;
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        String notiTitle = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        String fileHtml = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);
        LogUtils.e("action:" + intent.getExtras()
                + "EXTRA_TITLE:" + title
                + "\nEXTRA_MESSAGE:" + message
                + "\nEXTRA_EXTRA:" + extras
                + "\nEXTRA_MSG_ID:" + file
                + "\nEXTRA_NOTIFICATION_TITLE:" + notiTitle
                + "\nEXTRA_ALERT:" + content
                + "\nnotificationId:" + notificationId
                + "\nfileHtml:" + fileHtml);

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtils.d("JPushReceiver", "[MyReceiver] 接收Registration Id : " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.d("JPushReceiver", "收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.d("JPushReceiver", "收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.d("JPushReceiver", "用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            MessageListActivity.openReceive(context);
        } else {
            LogUtils.d("JPushReceiver", "Unhandled intent - " + intent.getAction());
        }
    }
}
