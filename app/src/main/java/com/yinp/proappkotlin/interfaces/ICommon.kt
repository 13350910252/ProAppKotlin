package com.yinp.proappkotlin.interfaces

import androidx.fragment.app.FragmentActivity
import com.yinp.tools.utils.LoadingUtils

/**
 * author:yinp
 * 2022/4/25+20:58
 * describe:
 */
interface ICommon {
    /**
     * 初始化以后才能使用
     */
    fun startCommon(activity: FragmentActivity, vararg method: () -> Unit)

    /**
     * 沉浸式
     */
    fun initImmersion()

    val loadingUtils: LoadingUtils
}