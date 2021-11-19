package com.yinp.proappkotlin.study.wanAndroid.data

import com.yinp.proappkotlin.web.data.WanAndroidData

/**
 * @author   :yinpeng
 * date      :2021/10/27
 * package   :com.yinp.proappkotlin.study.wanAndroid.data
 * describe  :
 */
data class WanHomeListData(
    val curPage: Int,
    val datas: List<Data>?,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) : WanAndroidData<WanHomeListData>() {
    data class Data(
        val alreadyInHomePage: Boolean,
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val host: String,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int,
        var isStick: Boolean
    ) : WanAndroidData<WanHomeListData>() {
        data class Tag(
            val name: String,
            val url: String
        )
    }
}





