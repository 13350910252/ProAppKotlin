package com.yinp.proappkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.utils.StatusBarUtil
import com.yinp.tools.utils.LoadingUtils
import com.yinp.tools.utils.ToastUtil

abstract class BaseActivity<VB : ViewBinding> : SkipActivity(), View.OnClickListener {
    protected lateinit var bd: VB
    protected lateinit var mContext: Context
    protected lateinit var mActivity: FragmentActivity

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
        findViewById<View>(R.id.view_status).apply {
            layoutParams.also {
                it.height = height
            }
        }
    }

    /**
     * 初始化点击事件
     */
    protected fun initClick(vararg views: View) {
        for (element in views) {
            element.setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {

    }

    abstract fun initViews()

    /**
     * 获取布局
     */
    protected abstract fun getBinding(): VB

    protected open fun bindData() {}


    /**
     * 显示加载
     */
    private val loading by lazy {
        LoadingUtils(supportFragmentManager)
    }

    protected fun showLoading(text: String) {
        loading.show(text)
    }

    protected fun showLoading(text: String, tag: String) {
        loading.show(text, tag)
    }

    protected fun hideLoading(tag: String) {
        loading.close(tag)
    }

    protected fun hideLoading() {
        loading.closeAll()
    }


    /**
     * 显示加载
     */

    protected fun showToast(text: String?) {
        ToastUtil.initToast(mContext, text)
    }

    protected fun showToast(@StringRes id: Int) {
        ToastUtil.initToast(mContext, id)
    }
}