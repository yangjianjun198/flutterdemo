package yjj.flutter;

import java.util.List;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

/**
 * created by yangjianjun on 2019/4/16
 * flutter plugin 注册器
 */
public class PluginRegister {

    public static void register(FlutterView flutterView, List<MethondChannelEntry> entries) {
        for (MethondChannelEntry entry : entries) {
            new MethodChannel(flutterView, entry.methodName).setMethodCallHandler(entry.messageHandler);
        }
    }

    public static class MethondChannelEntry {
        public MethodChannel.MethodCallHandler messageHandler;
        public String methodName;

        public MethondChannelEntry(MethodChannel.MethodCallHandler messageHandler, String methodName) {
            this.messageHandler = messageHandler;
            this.methodName = methodName;
        }
    }
}
