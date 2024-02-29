package com.xiaoming.utils;

import android.content.Context;

/**
 * px和dp互相转换工具
 */
public class UiUtil {
    public static int dp2px(Context context, float dpValue) {
        float mDensity = context.getResources().getDisplayMetrics().density;
        return (int) (mDensity * dpValue + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}