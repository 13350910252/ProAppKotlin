package com.yinp.proappkotlin.web.data

/**
 * @author   :yinpeng
 * date      :2021/11/17
 * package   :com.yinp.proappkotlin.web.data
 * describe  :
 */
sealed class WanResultDispose<T> {
    class Start<T> : WanResultDispose<T>()

    //成功
    data class Success<T>(var data: T) : WanResultDispose<T>()

    //失败
    data class Error<T>(val msg: String, val code: Int) : WanResultDispose<T>()

    //失败带Exception
    data class ErrorException<T>(val msg: String, val code: Int, val e: Throwable?) :
        WanResultDispose<T>()

    class LS<T> : WanResultDispose<T>()
}