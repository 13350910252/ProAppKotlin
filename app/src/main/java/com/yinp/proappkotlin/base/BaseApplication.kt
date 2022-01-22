package com.yinp.proappkotlin.base

import android.app.Application
import android.content.Context
import android.os.Process
import cn.bmob.v3.Bmob
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
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
    protected lateinit var mAppContext: Context

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

    //代码段可以防止内存泄露
    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(
                applicationContext
            )
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(
                applicationContext
            ).setDrawableSize(20f)
        }
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
        /**
         * Bmob后端云
         */
        initBmob()
    }

    private fun initBugly() {
        // 获取当前包名
        val packageName = mAppContext.packageName
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

    private fun initBmob() {
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(this, "b4f87a13ad741413472bec25c418e7a9");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }
}