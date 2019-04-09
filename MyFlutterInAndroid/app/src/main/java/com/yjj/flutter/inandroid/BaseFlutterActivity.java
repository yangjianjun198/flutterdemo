package com.yjj.flutter.inandroid;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * created by yangjianjun on 2019/4/7
 * flutter 容器
 */
public abstract class BaseFlutterActivity extends FlutterActivity {
    private List<String> methodNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMethodChannel();
    }

    public void addMethod(String... nameList) {
        if (nameList == null) {
            return;
        }
        for (String name : nameList) {
            methodNameList.add(name);
        }
    }

    private void registerMethodChannel() {
        new MethodChannel(getFlutterView(), "myPlugin").setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                if (methodNameList.contains(methodCall.method)) {
                    onHandleMethod(methodCall, result);
                    return;
                }
            }
        });
    }

    protected abstract void onHandleMethod(MethodCall methodCall, MethodChannel.Result result);
}
