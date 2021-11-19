package com.yinp.proappkotlin.study.wanAndroid.data

import com.yinp.proappkotlin.web.data.WanAndroidData

/**
 * @author   :yinpeng
 * date      :2021/10/29
 * package   :com.yinp.proappkotlin.study.wanAndroid.data
 * describe  :
 */
data class WanSysListData(
    val children: List<Children>?,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    var isTitle: Boolean //判断是否是标签
) : WanAndroidData<WanSysListData>() {
    constructor(
        courseId: Int,
        id: Int,
        name: String,
        order: Int,
        parentChapterId: Int,
        userControlSetTop: Boolean,
        visible: Int,
        isTitle: Boolean
    ) : this(
        null,
        courseId, id, name, order, parentChapterId, userControlSetTop, visible, isTitle
    ) {

    }

    data class Children(
        val children: List<Any>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
    )
}
