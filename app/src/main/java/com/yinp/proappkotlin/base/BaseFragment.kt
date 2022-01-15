package com.yinp.proappkotlin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.viewbinding.ViewBinding
import com.yinp.tools.utils.LoadingUtils
import com.yinp.tools.utils.ToastUtil

/**
 * @author   :yinpeng
 * date      :2021/10/9
 * package   :com.yinp.proappkotlin.base
 * describe  :
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), View.OnClickListener,
    LifecycleObserver {
    protected lateinit var bd: VB

    /**
     * 此处替代onActivityCreated方法
     */
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        requireActivity().lifecycle.addObserver(object : DefaultLifecycleObserver {
//            override fun onCreate(owner: LifecycleOwner) {
//                // 想做啥做点啥
//                initViews()
//                // 移除
//                owner.lifecycle.removeObserver(this)
//            }
//        })
//    }

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
//        lifecycle.addObserver(this)
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
    protected fun initClick(listener: View.OnClickListener, vararg views: View) {
        for (element in views) {
            element.setOnClickListener(listener)
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
        val intent = Intent(context, clazz)
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
        context?.let {
            if (url.isNullOrBlank()) {
                Toast.makeText(it, "不是正确的跳转地址", Toast.LENGTH_SHORT).show()
            } else {
                intent.setClassName(it, url)
                goToActivity(intent, -1)
            }
        }
    }

    /**
     * 获取布局
     */
    protected abstract fun getBinding(inflater: LayoutInflater, parent: ViewGroup?): VB
}