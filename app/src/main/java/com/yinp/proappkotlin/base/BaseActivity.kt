package com.yinp.proappkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.impl.CommonImpl
import com.yinp.proappkotlin.interfaces.ICommon
import com.yinp.tools.utils.LoadingUtils
import com.yinp.tools.utils.ToastUtil

abstract class BaseActivity<VB : ViewBinding> : SkipActivity(), View.OnClickListener,
    ICommon by CommonImpl() {
    protected lateinit var bd: VB
    protected lateinit var mContext: Context
    protected lateinit var mActivity: FragmentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = getBinding()
        setContentView(bd.root)
        mContext = this
        mActivity = this

        startCommon(this, { initImmersion() })
        initViews()
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


    protected fun showLoading(text: String) {
        loadingUtils.show(text)
    }

    protected fun showLoading(text: String, tag: String) {
        loadingUtils.show(text, tag)
    }

    protected fun hideLoading(tag: String) {
        loadingUtils.close(tag)
    }

    protected fun hideLoading() {
        loadingUtils.closeAll()
    }
}