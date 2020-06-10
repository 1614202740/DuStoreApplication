package com.dustoreapplication.android.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 16142
 * on 2020/6/2
 * @author 16142
 */
public class ImageTool {
    /**
     * 从服务器端获取网络图片
     * @param url 图片路径
     * @return
     */
    public static Bitmap getBitmapFromWeb(String url){
        if(url.length()==0||url.equals("null")){
            return null;
        }
        final Bitmap[] bitmap = new Bitmap[1];
        final boolean[] status = new boolean[]{true};
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                status[0] = false;
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                bitmap[0] = BitmapFactory.decodeStream(inputStream);
            }
        });
        while (bitmap[0]==null&&status[0]){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return bitmap[0];
    }

    public static Bitmap combineImagesToSameSize(Bitmap src, Bitmap dec){
        if(src==null||dec==null){
            return src==null?dec:src;
        }
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(dec,0,0,null);
        canvas.drawBitmap(src,0,0,null);
        return bitmap;
    }
}
