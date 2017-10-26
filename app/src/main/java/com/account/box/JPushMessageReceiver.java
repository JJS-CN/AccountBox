package com.account.box;

import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.JPushMessage;

/**
 * 说明：
 * Created by aa on 2017/10/26.
 */

public class JPushMessageReceiver extends cn.jpush.android.service.JPushMessageReceiver {
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
        //tag增删查改的操作会在此方法中回调结果。
        Log.e("JPushMessageReceiver", "onTagOperatorResult----" + jPushMessage.toString());
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);
        //查询某个tag与当前用户的绑定状态的操作会在此方法中回调结果。
        Log.e("JPushMessageReceiver", "onCheckTagOperatorResult----" + jPushMessage.toString());
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        //alias相关的操作会在此方法中回调结果。
        Log.e("JPushMessageReceiver", "onAliasOperatorResult----" + jPushMessage.toString());
    }
}
