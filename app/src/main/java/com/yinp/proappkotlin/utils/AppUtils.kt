package com.yinp.proappkotlin.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.yinp.proappkotlin.study.wanAndroid.data.WanLoginBean
import okhttp3.Cookie
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

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
    fun getHeightPixels(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    /**
     * 获取屏幕宽度的px
     *
     * @return
     */
    fun getWidthPixels(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getValue(value: String?): String {
        if (value.isNullOrEmpty()) {
            return "  "
        }
        return value
    }

    fun isLogin(context: Context): Boolean {
        val sharedPrefsCookiePersistor = SharedPrefsCookiePersistor(context)
        val cookies: List<Cookie> = sharedPrefsCookiePersistor.loadAll()
        return if (cookies.isNotEmpty()) {
            val wanLoginBean = WanLoginBean.getUserInfo(context)
            if (wanLoginBean != null) {
                !TextUtils.isEmpty(wanLoginBean.username)
            } else {
                false
            }
        } else {
            false
        }
    }

    /**
     * 获取Decode的中文
     *
     * @param encodeName
     * @return
     */
    fun getDecodeName(encodeName: String?): String {
        var decodeName = ""
        encodeName?.let {
            try {
                decodeName = URLDecoder.decode(it, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
        return decodeName
    }
}