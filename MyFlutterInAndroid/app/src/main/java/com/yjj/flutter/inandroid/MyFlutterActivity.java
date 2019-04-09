package com.yjj.flutter.inandroid;

import android.os.Bundle;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * created by yangjianjun on 2019/4/7
 */
public class MyFlutterActivity extends BaseFlutterActivity {
    @Override
    public void addMethod(String... name) {
        super.addMethod(name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMethod("getSharedText");
    }

    @Override
    protected void onHandleMethod(MethodCall methodCall, MethodChannel.Result result) {
        if("getSharedText".equals(methodCall.method)) {
            result.success("测试从native获取到的数据");
        }
    }
}
