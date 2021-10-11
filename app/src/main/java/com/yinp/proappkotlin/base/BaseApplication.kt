package com.yinp.proappkotlin.base

import android.app.Application
import android.content.Context
import android.os.Process
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import com.yinp.proappkotlin.utils.AppUtils

/**
 * @author   :yinpeng
 * date      :2021/10/9
 * package   :com.yinp.proappkotlin
 * describe  :
 */
open class BaseApplication : Application() {
    protected var mAppContext: Context? = null

    companion object {
        private val TYPE_DEBUG = "type_debug"
        private val TYPE_RELEASE = "type_release"
        private val TYPE = TYPE_DEBUG
    }

    override fun onCreate() {
        super.onCreate()
        mAppContext = this
        initSomething()
    }

    private fun initSomething() {
        /**
         * 腾讯得存储方案替代SharedPreferences
         * 可以在程序崩溃后仍然正确存储值
         */
        MMKV.initialize(this)
        /**
         * 腾讯的bugly
         */
        initBugly()
    }

    private fun initBugly() {
        // 获取当前包名
        val packageName = mAppContext!!.packageName
        // 获取当前进程名
        val processName: String? = AppUtils.getProcessName(Process.myPid())
        // 设置是否为上报进程
        val strategy: CrashReport.UserStrategy = CrashReport.UserStrategy(mAppContext)
        strategy.isBuglyLogUpload = processName == null || processName == packageName
        if (TYPE == TYPE_DEBUG) {
            CrashReport.initCrashReport(applicationContext, "03ed601592", true, strategy)
        } else {
            CrashReport.initCrashReport(applicationContext, "03ed601592", false, strategy)
        }
    }
}