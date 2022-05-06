package com.yinp.tools.utils

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
    fun getHeightPixels() = Resources.getSystem().displayMetrics.heightPixels

    /**
     * 获取屏幕宽度的px
     *
     * @return
     */
    fun getWidthPixels() = Resources.getSystem().displayMetrics.widthPixels
}