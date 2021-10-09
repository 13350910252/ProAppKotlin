package com.yinp.proappkotlin

import android.os.Build
import android.view.Window
import android.view.WindowManager

/**
 * @author   :yinpeng
 * date      :2021/10/9
 * package   :com.yinp.proappkotlin
 * describe  :
 */
object AppUtils {
    /**
     * 实现全屏得效果
     */
    fun setFullScreen(window: Window) {
        val lp = window.attributes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.attributes = lp;
    }
}