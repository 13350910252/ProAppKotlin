package com.yinp.proappkotlin.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.utils.StatusBarUtil

abstract class BaseActivity<VB : ViewBinding> : FragmentActivity(), View.OnClickListener {
    protected lateinit var bd: VB
    protected lateinit var mContext: Context
    protected lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = getBinding()
        setContentView(bd.root)
        mContext = this
        mActivity = this

        StatusBarUtil.setTranslucentStatus(this)
        initViews()
    }

    /**
     * 设置占位View的高度，主要是用于浸入式状态栏
     *
     * @param height 状态栏高度
     */
    protected fun setStatusBarHeight(height: Int) {
        val view = findViewById<View>(R.id.view_status)
        val params = view.layoutParams
        params.height = height
    }

    /**
     * 初始化点击事件
     */
    protected fun initClick(listener: View.OnClickListener, vararg views: View) {
        for (element in views) {
            element.setOnClickListener(listener)
        }
    }

    override fun onClick(v: View?) {

    }

    abstract fun initViews()

    /**
     * 获取布局
     */
    protected abstract fun getBinding(): VB
}