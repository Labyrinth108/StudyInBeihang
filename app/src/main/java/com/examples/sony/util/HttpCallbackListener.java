package com.examples.sony.util;

/**
 * Created by SONY on 2016/2/19.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
