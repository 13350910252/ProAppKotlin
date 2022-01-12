package com.yinp.proappkotlin.study.wanAndroid.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * @author   :yinpeng
 * date      :2022/1/12
 * package   :com.yinp.proappkotlin.study.wanAndroid.data
 * describe  :
 */
@JsonClass(generateAdapter = true)
data class CollectionListBean(
    @Json(name = "curPage")
    val curPage: Int = 0,
    @Json(name = "datas")
    val datas: List<Data> = listOf(),
    @Json(name = "offset")
    val offset: Int = 0,
    @Json(name = "over")
    val over: Boolean = false,
    @Json(name = "pageCount")
    val pageCount: Int = 0,
    @Json(name = "size")
    val size: Int = 0,
    @Json(name = "total")
    val total: Int = 0
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "author")
        val author: String = " ",
        @Json(name = "chapterId")
        val chapterId: Int = 0,
        @Json(name = "chapterName")
        val chapterName: String = "",
        @Json(name = "courseId")
        val courseId: Int = 0,
        @Json(name = "desc")
        val desc: String = "",
        @Json(name = "envelopePic")
        val envelopePic: String = "",
        @Json(name = "id")
        val id: Int = 0,
        @Json(name = "link")
        val link: String = "",
        @Json(name = "niceDate")
        val niceDate: String = "",
        @Json(name = "origin")
        val origin: String = "",
        @Json(name = "originId")
        val originId: Int = 0,
        @Json(name = "publishTime")
        val publishTime: Long = 0,
        @Json(name = "title")
        val title: String = "",
        @Json(name = "userId")
        val userId: Int = 0,
        @Json(name = "visible")
        val visible: Int = 0,
        @Json(name = "zan")
        val zan: Int = 0,
        @Json(name = "isCollect")
        var isCollect: Boolean = true
    )
}
