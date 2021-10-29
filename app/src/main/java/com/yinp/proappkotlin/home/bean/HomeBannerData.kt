package com.yinp.proappkotlin.home.bean

import com.yinp.proappkotlin.web.data.WanAndroidData

/**
 * @author   :yinpeng
 * date      :2021/10/22
 * package   :com.yinp.proappkotlin.home.bean
 * describe  :
 */
data class HomeBannerData(
    val id: String,
    val desc: String,
    val imagePath: String,
    val isVisible: String,
    val order: String,
    val title: String,
    val type: String,
    val url: String
) :
    WanAndroidData<HomeBannerData>() {
        constructor(imagePath: String):this("","",imagePath,"","","","",""){

        }
}