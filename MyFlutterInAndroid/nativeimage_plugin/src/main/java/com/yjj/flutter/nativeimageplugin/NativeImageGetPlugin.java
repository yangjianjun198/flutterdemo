package com.yjj.flutter.nativeimageplugin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import yjj.flutter.BaseNativePlugin;

/**
 * created by yangjianjun on 2019/4/18
 * Native 图片获取
 */
public class NativeImageGetPlugin extends BaseNativePlugin {
    private static ExecutorService singleService = Executors.newSingleThreadExecutor();
    public static final String FLUTTER_IMAGES = "flutter_images";
    private Context context;

    public NativeImageGetPlugin(Context context) {
        this.context = context;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if ("getImage".equals(methodCall.method)) {
            String imageId = methodCall.arguments();
            getNativeImage(imageId, result);
        }
    }

    private void getNativeImage(final String imageId, final MethodChannel.Result result) {
        String drawableName = getImageId(imageId);
        int drawableId = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
        if (drawableId == 0) {
            drawableId = context.getResources().getIdentifier(drawableName, "mipmap", context.getPackageName());
        }
        final String successPath = getImagePath() + File.separator + imageId;
        File successPathFile = new File(successPath);
        if (successPathFile.exists()) {
            result.success(successPath);
            return;
        }
        boolean fileNewHasError = true;
        File pathFile = new File(getImagePath());
        if (!pathFile.exists()) {
            if (!pathFile.mkdirs()) {
                result.success("");
                return;
            }
        }
        boolean newSuccessFile = false;
        try {
            newSuccessFile = successPathFile.createNewFile();
            fileNewHasError = false;
        } catch (IOException e) {
        }
        if (fileNewHasError) {
            result.success("");
            return;
        }
        if (!newSuccessFile) {
            result.success("");
            return;
        }
        final int finalDrawableId = drawableId;
        singleService.execute(new Runnable() {
            @Override
            public void run() {
                performCompressImage(imageId, result, finalDrawableId, successPath);
            }
        });
    }

    private void performCompressImage(String imageId, MethodChannel.Result result, int drawableId, String successPath) {
        FileOutputStream fos = null;
        boolean fileSaveSuccess = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        try {
            fos = new FileOutputStream(successPath);
            boolean isSuccess = bitmap.compress(getCompressFormat(imageId), 100, fos);
            if (isSuccess) {
                fileSaveSuccess = true;
            }
        } catch (FileNotFoundException e) {

        }
        result.success(fileSaveSuccess ? successPath : "");
    }

    private String getImageId(String imageId) {
        if (imageId.indexOf(".") < 0) {
            return imageId;
        }
        return imageId.substring(0, imageId.indexOf("."));
    }

    private Bitmap.CompressFormat getCompressFormat(String name) {
        if (name.indexOf(".") < 0) {
            return Bitmap.CompressFormat.JPEG;
        }
        String postfix = name.substring(name.indexOf(".") + 1);
        if (TextUtils.isEmpty(postfix)) {
            return Bitmap.CompressFormat.JPEG;
        }
        if (postfix.equalsIgnoreCase("jpeg") || postfix.equalsIgnoreCase("jpg")) {
            return Bitmap.CompressFormat.JPEG;
        }
        if (postfix.equalsIgnoreCase("png")) {
            return Bitmap.CompressFormat.PNG;
        }
        if (postfix.equalsIgnoreCase("webp")) {
            return Bitmap.CompressFormat.WEBP;
        }
        return Bitmap.CompressFormat.JPEG;
    }

    private String getImagePath() {
        String state;
        try {
            state = Environment.getExternalStorageState();
        } catch (NullPointerException ignore) {
            state = "";
        }
        String imagePath = context == null ? "" : FLUTTER_IMAGES;

        if (Environment.MEDIA_MOUNTED.equals(state) && Environment.getExternalStorageDirectory().canWrite()) {
            return Environment.getExternalStorageDirectory().getPath() + imagePath;
        }
        return context.getFilesDir() + File.separator + FLUTTER_IMAGES;
    }

    @Override
    public String getPluginName() {
        return "plugins.flutter.yjj/native_image_getter";
    }
}
