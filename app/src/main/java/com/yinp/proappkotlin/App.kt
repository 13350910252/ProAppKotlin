package com.yinp.proappkotlin

import android.content.Context
import com.yinp.proappkotlin.base.BaseApplication

/**
 * @author   :yinpeng
 * date      :2021/10/9
 * package   :com.yinp.proappkotlin.base
 * describe  :
 */
const val TAG = "yinp_yinp"

class App : BaseApplication() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}