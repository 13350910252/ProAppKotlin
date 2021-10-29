package com.yinp.proappkotlin.study.wanAndroid.data

import com.yinp.proappkotlin.web.data.WanAndroidData

/**
 * @author   :yinpeng
 * date      :2021/10/22
 * package   :com.yinp.proappkotlin.home.bean
 * describe  :
 */
data class WanHomeBannerData(
    val id: String,
    val desc: String,
    val imagePath: String,
    val isVisible: String,
    val order: String,
    val title: String,
    val type: String,
    val url: String
) :
    WanAndroidData<WanHomeBannerData>() {
        constructor(imagePath: String):this("","",imagePath,"","","","",""){

        }
}