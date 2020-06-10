package com.dustoreapplication.android.tool;

import android.content.Context;

/**
 * Created by 16142
 * on 2020/5/14
 * @author 16142
 */
public class ScreenTool {

    /**
     * 根据手机的分辨率从dp单位转换为px单位
     * @param context 上下文对象
     * @param dpValue dp单位数值
     * @return px单位数值
     */
    public static int dip2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale+0.5f);
    }

    /**
     * 根据手机的分辨率从dp单位转换为px单位
     * @param context 上下文对象
     * @param pxValue px单位数值
     * @return dp单位数值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
