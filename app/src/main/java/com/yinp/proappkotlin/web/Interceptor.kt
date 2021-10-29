package com.lp.myapplication.net

import android.annotation.SuppressLint
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

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
            Log.w("-------", "OkHttp: $message")
        }
        loggingInterceptor.level = level
        return loggingInterceptor
    }
}