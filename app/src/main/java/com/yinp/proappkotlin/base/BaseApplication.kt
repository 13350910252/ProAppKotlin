package com.yinp.proappkotlin.base

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV

/**
 * @author   :yinpeng
 * date      :2021/10/9
 * package   :com.yinp.proappkotlin
 * describe  :
 */
open class BaseApplication : Application() {
    protected var mAppContext: Context? = null
    override fun onCreate() {
        super.onCreate()
        initSomething()
    }

    fun initSomething() {
        /**
         * 腾讯得存储方案替代SharedPreferences
         * 可以在程序崩溃后仍然正确存储值
         */
        MMKV.initialize(this)
    }
}