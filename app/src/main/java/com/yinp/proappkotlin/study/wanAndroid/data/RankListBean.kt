package com.yinp.proappkotlin.study.wanAndroid.data

import com.yinp.proappkotlin.web.data.WanAndroidData

/**
 * @author   :yinpeng
 * date      :2022/1/11
 * package   :com.yinp.proappkotlin.study.wanAndroid.data
 * describe  :
 */
data class RankListBean(
    val curPage: Int,
    val datas: List<Data>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) : WanAndroidData<RankListBean>() {
    data class Data(
        val coinCount: Int,
        val level: Int,
        val nickname: String,
        val rank: String,
        val userId: Int,
        val username: String
    )
}
