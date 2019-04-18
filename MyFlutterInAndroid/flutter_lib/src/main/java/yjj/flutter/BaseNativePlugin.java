package yjj.flutter;

import io.flutter.plugin.common.MethodChannel;

/**
 * created by yangjianjun on 2019/4/18
 * native plugin
 */
public abstract class BaseNativePlugin implements MethodChannel.MethodCallHandler {
    public abstract String getPluginName();
}
