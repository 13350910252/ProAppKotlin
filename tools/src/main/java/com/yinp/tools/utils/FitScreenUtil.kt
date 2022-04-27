package com.yinp.tools.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * author:yinp
 * 2022/4/24+20:36
 * describe:
 */
class FitScreenUtil {
    private var sNoncompatDensity = 0f
    private var sNoncompatScaledDensity = 0f

    fun setCustomDensity(activity: Activity, application: Application) {
        val appDisplayMetrics = application.resources.displayMetrics
        if (sNoncompatDensity == 0f) {
            sNoncompatDensity = appDisplayMetrics.density
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity
            //            Log.d("abcd", "setCustomDensity: " + sNoncompatDensity);
//            Log.d("abcd", "setCustomDensity: " + sNoncompatScaledDensity);
            //系统配置修改监听，比如字体变化
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {}
            })
        }
        /**
         * 以360dp为例子 1920px*1080px至640dp*360dp  即density=3  1px = 3dp;
         */
        val targetDensity = (appDisplayMetrics.widthPixels / 360).toFloat()
        val targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity)
        val targetDensityDpi = (targetDensity * 160).toInt()
        //        Log.d("abcd", "targetDensity: " + targetDensity);
//        Log.d("abcd", "targetScaledDensity: " + targetScaledDensity);
//        Log.d("abcd", "targetDensityDpi: " + targetDensityDpi);
        appDisplayMetrics.density = targetDensity
        appDisplayMetrics.scaledDensity = targetScaledDensity
        appDisplayMetrics.densityDpi = targetDensityDpi
        val activityDisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaledDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }
}