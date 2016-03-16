package com.coolweather.app.utils;

/**
 * Created by huangjinlong on 2016/2/16.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
