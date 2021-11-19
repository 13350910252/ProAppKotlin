package com.yinp.proappkotlin.web.data

/**
 * @author   :yinpeng
 * date      :2021/10/22
 * package   :com.yinp.proappkotlin.web
 * describe  :
 */
open class WanAndroidData<T> : BaseRespData<T>() {
    var data: T? = null
    var errorCode = 0
    var errorMsg: String = ""

    /**
     * 用于标识来处理事件
     */
    var eventCode = -1
    var exContent = ""
    var eventType = ""
    var success = false

}
