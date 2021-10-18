package com.yinp.tools.utils

import android.content.Context

/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.tools.utils
 * describe  :
 */
object ToolsUtils {
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