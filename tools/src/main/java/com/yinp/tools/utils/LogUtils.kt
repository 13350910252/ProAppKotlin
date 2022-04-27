package com.yinp.tools.utils

import android.util.Log

/**
 * author:yinp
 * 2022/4/24+20:42
 * describe:
 */
class LogUtils {
    var isDebug = true

    private val APP_TAG = "LogUtils"

    /**
     * 获取相关数据:类名,方法名,行号等.用来定位行<br></br>
     * at cn.utils.MainActivity.onCreate(MainActivity.java:17) 就是用來定位行的代碼<br></br>
     *
     * @return [ Thread:main, at
     * cn.utils.MainActivity.onCreate(MainActivity.java:17)]
     */
    private fun getFunctionName(): String? {
        val sts = Thread.currentThread().stackTrace
        for (st in sts) {
            if (st.isNativeMethod) {
                continue
            }
            if (st.className == Thread::class.java.name) {
                continue
            }
            if (st.className == LogUtils::class.java.name) {
                continue
            }
            return ("[ Thread:" + Thread.currentThread().name + ", at " + st.className + "." + st.methodName
                    + "(" + st.fileName + ":" + st.lineNumber + ")" + " ]")
        }
        return null
    }


    fun v(msg: String) {
        if (isDebug) {
            Log.v(APP_TAG, getMsgFormat(msg)!!)
        }
    }

    fun v(tag: String?, msg: String) {
        if (isDebug) {
            Log.v(tag, getMsgFormat(msg)!!)
        }
    }


    fun d(msg: String) {
        if (isDebug) {
            Log.d(APP_TAG, getMsgFormat(msg)!!)
        }
    }

    fun d(tag: String?, msg: String) {
        if (isDebug) {
            Log.d(tag, getMsgFormat(msg)!!)
        }
    }


    fun i(msg: String) {
        if (isDebug) {
            Log.i(APP_TAG, getMsgFormat(msg)!!)
        }
    }

    fun i(tag: String?, msg: String) {
        if (isDebug) {
            Log.i(tag, getMsgFormat(msg)!!)
        }
    }


    fun w(msg: String) {
        if (isDebug) {
            Log.w(APP_TAG, getMsgFormat(msg)!!)
        }
    }

    fun w(tag: String?, msg: String) {
        if (isDebug) {
            Log.w(tag, getMsgFormat(msg)!!)
        }
    }


    fun e(msg: String) {
        if (isDebug) {
            Log.e(APP_TAG, getMsgFormat(msg)!!)
        }
    }

    fun e(tag: String?, msg: String) {
        if (isDebug) {
            Log.e(tag, getMsgFormat(msg)!!)
        }
    }

    /**
     * 输出格式定义
     */
    private fun getMsgFormat(msg: String): String? {
        return msg + " ;" + getFunctionName()
    }

}