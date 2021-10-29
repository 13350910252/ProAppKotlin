package com.yinp.tools.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * @author yinp.
 * @title
 * @description 今日头条屏幕适配方案，对屏幕进行适配,在onCreate（）中使用
 * px = density * dp;
 * density = dpi / 160;
 * px = dp * (dpi / 160);
 * dpi 一英寸多少个像素点，density 屏幕密度，px是像素点。ldpi，密度为120dpi，mdpi密度为160dpi，hdpi密度为240dpi，xhdpi密度为320dpi，xxhdpi3
 * 比如1920*1080，屏幕尺寸为5寸，那么dpi为440px，既density为440/160 = 2.75，那么以dp为宽度则是1080/2.75=392，那么如果图片不是这个尺寸就会
 * 有拉伸和收缩
 * 原理就是获取当前手机的density，
 * @date 2019/7/23,8:26.
 */

public class FitScreenUtil {
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static void setCustomDensity(Activity activity, final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
//            Log.d("abcd", "setCustomDensity: " + sNoncompatDensity);
//            Log.d("abcd", "setCustomDensity: " + sNoncompatScaledDensity);
            //系统配置修改监听，比如字体变化
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        /**
         * 以360dp为例子 1920px*1080px至640dp*360dp  即density=3  1px = 3dp;
         */
        float targetDensity = appDisplayMetrics.widthPixels / 360;
        float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        int targetDensityDpi = (int) (targetDensity * 160);
//        Log.d("abcd", "targetDensity: " + targetDensity);
//        Log.d("abcd", "targetScaledDensity: " + targetScaledDensity);
//        Log.d("abcd", "targetDensityDpi: " + targetDensityDpi);
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
