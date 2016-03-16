package com.coolweather.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.coolweather.app.service.AutoUpdateService;

/**
 * Created by huangjinlong on 2016/2/17.
 */
public class AutoUpdateReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
