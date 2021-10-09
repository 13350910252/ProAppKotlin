package com.yinp.proappkotlin.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @author   :yinpeng
 * date      :2021/10/9
 * package   :com.yinp.proappkotlin.base
 * describe  :
 */
open class BaseFragment : Fragment() {
    /**
     * 初始化点击事件
     */
    protected fun initClick(listener: View.OnClickListener, vararg views: View) {
        for (element in views) {
            element.setOnClickListener(listener)
        }
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
}