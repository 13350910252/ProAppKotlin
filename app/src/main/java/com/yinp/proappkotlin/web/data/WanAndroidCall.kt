package com.yinp.proappkotlin.web.data

/**
 * @author   :yinpeng
 * date      :2021/10/29
 * package   :com.yinp.proappkotlin.web.data
 * describe  :
 */
abstract class WanAndroidCall {
    open fun onStart() {}
    open fun onCatch(message: String) {}
    open fun onSuccess() {}
}