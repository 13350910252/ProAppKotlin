package com.yinp.tools.utils

import android.content.Context
import android.content.res.Resources

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
    fun getHeightPixels(context: Context) = context.resources.displayMetrics.heightPixels

    /**
     * 获取屏幕宽度的px
     *
     * @return
     */
    fun getWidthPixels(context: Context) = context.resources.displayMetrics.widthPixels

    inline fun <reified T> dpToPx(value: T): T {
        return when (T::class) {
            Float::class -> (Resources.getSystem().displayMetrics.density * value as Float + 0.5f) as T
            Int::class -> (Resources.getSystem().displayMetrics.density * value as Int + 0.5f).toInt() as T
            else -> throw RuntimeException()
        }
    }

    inline fun <reified T> pxToDp(value: T): T {
        return when (T::class) {
            Float::class -> (Resources.getSystem().displayMetrics.density / value as Float + 0.5f) as T
            Int::class -> (Resources.getSystem().displayMetrics.density / value as Int + 0.5f).toInt() as T
            else -> throw RuntimeException()
        }
    }
}