package com.yinp.proappkotlin.utils

import android.content.Context
import android.os.Build
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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.attributes = lp
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

    inline fun <reified T> dpToPx(context: Context, value: Float): T {
        val scale = context.resources.displayMetrics.density
        return when (T::class) {
            Float::class -> (scale * value + 0.5f) as T
            Int::class -> (scale * value + 0.5f).toInt() as T
            else -> throw IllegalStateException("Type not supported")
        }
    }

    /**
     * px转dp
     *
     * @return
     */

    inline fun <reified T> pxToDp(context: Context, value: Float): T {
        val scale = context.resources.displayMetrics.density
        return when (T::class) {
            Float::class -> (value / scale + 0.5f) as T
            Int::class -> (value / scale + 0.5f).toInt() as T
            else -> throw IllegalStateException("Type not supported")
        }
    }

    fun getValue(value: String?): String {
        if (value.isNullOrEmpty()) {
            return "  "
        }
        return value
    }
//    fun isLogin(context: Context?): Boolean {
//        val sharedPrefsCookiePersistor = SharedPrefsCookiePersistor(context)
//        val cookies: List<Cookie> = sharedPrefsCookiePersistor.loadAll()
//        return if (cookies.isNotEmpty()) {
//            val wanLoginBean: WanLoginBean = WanLoginBean.getUserInfo(context)
//            if (wanLoginBean != null) {
//                !TextUtils.isEmpty(wanLoginBean.getUsername())
//            } else {
//                false
//            }
//        } else {
//            false
//        }
//    }
}