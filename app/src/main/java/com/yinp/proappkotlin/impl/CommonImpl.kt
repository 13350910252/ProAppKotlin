package com.yinp.proappkotlin.impl

import androidx.fragment.app.FragmentActivity
import com.yinp.proappkotlin.interfaces.ICommon
import com.yinp.tools.utils.LoadingUtils

/**
 * author:yinp
 * 2022/4/25+20:59
 * describe:
 */
class CommonImpl : ICommon {
    private lateinit var mActivity: FragmentActivity
    override fun startCommon(activity: FragmentActivity, vararg method: () -> Unit) {
        mActivity = activity

    }

    /**
     * 沉浸式
     */
    override fun initImmersion() {

    }

    /**
     * 加载中弹窗
     */
    override val loadingUtils by lazy {
        LoadingUtils(mActivity.supportFragmentManager).apply {
            mActivity.lifecycle.addObserver(this)
        }
    }

}