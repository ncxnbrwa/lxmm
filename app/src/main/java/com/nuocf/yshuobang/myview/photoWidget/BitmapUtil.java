package com.nuocf.yshuobang.myview.photoWidget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;

/**
 /**
 * =================yangyf================
 *
 * @Author: yangyf
 * @Time : 2016/10/12 16:18
 * @Action :图片操作
 */
public class BitmapUtil {
    /**
     * 将bitmap保存到本地相册(Content provider)
     * 可以直接在相册看到,并非保存到sd卡
     *
     * @param context
     * @param bmp
     */
    public static void saveImage2Gallery(Context context, Bitmap bmp, final SaveImageCall imageCall) {
        try {
            // 其次把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, "title", "description");
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment
                    .getExternalStorageDirectory())));
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (imageCall != null) imageCall.onSucce();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (imageCall != null) imageCall.onFault();
                }
            });
        }


    }
}  