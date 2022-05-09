package com.yinp.proappkotlin.impl

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.interfaces.ICommon
import com.yinp.proappkotlin.utils.StatusBarUtil
import com.yinp.tools.utils.LoadingUtils

/**
 * author:yinp
 * 2022/4/25+20:59
 * describe:
 */
class CommonImpl : ICommon {
    private lateinit var mActivity: FragmentActivity
    override fun startCommon(activity: FragmentActivity, vararg methods: () -> Unit) {
        mActivity = activity
        Log.d("abcd", "startCommon: ${activity}")
        for (method in methods) {
            method.invoke()
        }
    }

    /**
     * 沉浸式
     */
    override fun initImmersion() {
        StatusBarUtil.setTranslucentStatus(mActivity)
        mActivity.findViewById<View>(R.id.view_status)?.apply {
            layoutParams.height = StatusBarUtil.getStatusBarHeight(mActivity)
        }
    }

    /**
     * 加载中弹窗
     */
    override val loadingUtils by lazy {
        LoadingUtils(mActivity.supportFragmentManager).apply {
            mActivity.lifecycle.addObserver(this)
        }
    }

    override fun showToast(text: String, duration: Int) {
        Toast.makeText(mActivity, text, duration).show()
    }

}