package com.dustoreapplication.android.tool;

import android.content.ContextWrapper;
import android.database.Cursor;
import android.provider.MediaStore;

import com.dustoreapplication.android.ui.activity.ImageModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContentResolverTool {
    private static final String TAG = "ContentResolverHelper";
    private static Gson gson;

    private ContentResolverTool() {
    }

    /**
     * 从系统相册中获取图片
    */
    public static List<ImageModel> queryImagesFromExternal(ContextWrapper contextWrapper) {
        List<ImageModel> imageModelList = new ArrayList<>();
        Cursor cursor = contextWrapper.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            gson = new Gson();
            while (cursor.moveToNext()) {
                try {
                    JSONObject json = new JSONObject();
                    String[] columnNames = cursor.getColumnNames();
                    for (String columnName : columnNames) {
                        String s = cursor.getString(cursor.getColumnIndex(columnName));
                        json.put(columnName, s);
                    }
                    ImageModel imageModel = gson.fromJson(json.toString(), ImageModel.class);
                    imageModelList.add(imageModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return imageModelList;
    }
}
