package com.yinp.proappkotlin.utils

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

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

    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!processName.isNullOrEmpty()) {
                processName = processName.trim()
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }

    /**
     * 获取屏幕高度的px
     *
     * @return
     */
    fun getHeightPixels(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取屏幕宽度的px
     *
     * @return
     */
    fun getWidthPixels(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * dp转px
     *
     * @return
     */
    fun dpToPx(context: Context, value: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return scale * value + 0.5f
    }

    fun dpToPx(context: Context, value: Int): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (scale * value + 0.5f).toInt()
    }

    /**
     * px转dp
     *
     * @return
     */
    fun pxToDp(context: Context, value: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return value / scale + 0.5f
    }

    fun pxToDp(context: Context, value: Int): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (value / scale + 0.5f).toInt()
    }
}