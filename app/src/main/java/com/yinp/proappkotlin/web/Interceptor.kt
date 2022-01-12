package com.yinp.proappkotlin.web

import android.util.Log
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

class Interceptor {
    /**
     * header拦截器
     * */
    fun headerInterceptor(): Interceptor {
        //新建header拦截器
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json;charSet=UTF-8")
                //                    .addHeader("os", "2").addHeader("over", Build.VERSION.RELEASE)
                //                    .addHeader("version", "1")
                .build()
            chain.proceed(request)
        }
    }

    /**
     * 日志拦截器
     * */
    fun loggingInterceptor(): HttpLoggingInterceptor {
        //日志显示级别
        val level = HttpLoggingInterceptor.Level.BODY
        //新建log拦截器
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            if (message.length > 2500) {
                var i = 0
                while (i < message.length) {
                    //当前截取的长度<总长度则继续截取最大的长度来打印
                    if (i + 2500 < message.length) {
                        Log.w(
                            "------",
                            message.substring(i, i + 2500)
                        )
                    } else {
                        //当前截取的长度已经超过了总长度，则打印出剩下的全部信息
                        Log.w(
                            "------",
                            message.substring(i)
                        )
                    }
                    i += 2500
                }
            } else {
                //直接打印
                Log.w("------", message)
            }
        }
        loggingInterceptor.level = level
        return loggingInterceptor
    }
}