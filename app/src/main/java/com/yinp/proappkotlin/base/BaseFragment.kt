package com.yinp.proappkotlin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.yinp.tools.utils.LoadingUtils
import com.yinp.tools.utils.ToastUtil

/**
 * @author   :yinpeng
 * date      :2021/10/9
 * package   :com.yinp.proappkotlin.base
 * describe  :
 */
abstract class BaseFragment<VB : ViewBinding> : SkipFragment(), View.OnClickListener {
    lateinit var bd: VB

    /**
     * 初始化一些数据
     */
    abstract fun initViews()

    /**
     * 创建布局
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bd = getBinding(inflater, container)
        return bd.root
    }

    /**
     * 初始化界面
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {

    }

    /**
     * 初始化点击事件
     */
    protected fun initClick(vararg views: View) {
        for (element in views) {
            element.setOnClickListener(this)
        }
    }

    /**
     * 显示加载
     */
    private val loading by lazy {
        LoadingUtils()
    }

    protected fun showLoading(text: String) {
        loading.show(parentFragmentManager, text)
    }

    protected fun showLoading(text: String, tag: String) {
        loading.show(parentFragmentManager, text, tag)
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
        ToastUtil.initToast(requireContext(), text)
    }

    protected fun showToast(@StringRes id: Int) {
        ToastUtil.initToast(requireContext(), id)
    }

    fun gotoActivity(url: String?) {
        val intent = Intent()
        context?.let {
            if (url.isNullOrBlank()) {
                Toast.makeText(it, "不是正确的跳转地址", Toast.LENGTH_SHORT).show()
            } else {
                intent.setClassName(it, url)
                startActivity(intent)
            }
        }
    }

    /**
     * 获取布局
     */
    protected abstract fun getBinding(inflater: LayoutInflater, parent: ViewGroup?): VB
}