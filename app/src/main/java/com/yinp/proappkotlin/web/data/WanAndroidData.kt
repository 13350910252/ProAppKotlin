package com.yinp.proappkotlin.web.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author   :yinpeng
 * date      :2021/10/22
 * package   :com.yinp.proappkotlin.web
 * describe  :
 */
@JsonClass(generateAdapter = true)
open class WanAndroidData<T> (

    @Json(name = "data")
    val data: T? = null,
    @Json(name = "errorCode")
    val errorCode: Int = 0,
    @Json(name = "errorMsg")
    val errorMsg: String = ""
): BaseRespData<T>()
//    /**
//     * 用于标识来处理事件
//     */
//    var eventCode = -1
//    var exContent = ""
//    var eventType = ""
//    var success = false

//}
