package com.yinp.proappkotlin.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.utils.StatusBarUtil

abstract class BaseActivity<VB : ViewBinding> : FragmentActivity(), View.OnClickListener {
    protected lateinit var bd: VB
    protected val mContext: Context
    protected val mActivity: Activity

    init {
        mContext = this
        mActivity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = getBinding()
        setContentView(bd.root)

        StatusBarUtil.setTranslucentStatus(this)
        initViews()
    }

    /**
     * 设置占位View的高度，主要是用于浸入式状态栏
     *
     * @param height 状态栏高度
     */
    protected open fun setStatusBarHeight(height: Int) {
        val view: View = findViewById(R.id.view_status)
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

    fun goToActivity(intent: Intent) {
        goToActivity(intent, -1)
    }

    fun goToActivity(intent: Intent, requestCode: Int) {
        if (requestCode == -1) {
            startActivity(intent)
        } else {
            startActivityForResult(intent, requestCode)
        }
    }

    fun goToActivity(clazz: Class<*>) {
        goToActivity(clazz, null, -1)
    }

    fun goToActivity(clazz: Class<*>, bundle: Bundle) {
        goToActivity(clazz, bundle, -1)
    }

    fun goToActivity(clazz: Class<*>, requestCode: Int) {
        goToActivity(clazz, null, requestCode)
    }

    fun goToActivity(clazz: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(this, clazz)
        bundle?.let {
            intent.putExtras(it)
        }
        if (requestCode == -1) {
            startActivity(intent)
        } else {
            startActivityForResult(intent, requestCode)
        }
    }

    fun goToActivity(url: String?) {
        val intent = Intent()
        if (url.isNullOrBlank()) {
            Toast.makeText(mContext, "不是正确的跳转地址", Toast.LENGTH_SHORT).show()
        } else {
            intent.setClassName(mContext, url)
            goToActivity(intent, -1)
        }
    }

    /**
     * 获取布局
     */
    protected abstract fun getBinding(): VB
}