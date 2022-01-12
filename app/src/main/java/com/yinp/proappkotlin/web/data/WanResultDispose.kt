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
    data class Error<T>(val errMsg: String) : WanResultDispose<T>()

    //errorCode
    data class CodeError<T>(val msg: String, val code: Int) : WanResultDispose<T>()

    class LS<T> : WanResultDispose<T>()
}