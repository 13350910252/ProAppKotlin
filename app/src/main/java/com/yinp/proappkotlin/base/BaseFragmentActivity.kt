package com.yinp.proappkotlin.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentActivity<TT : ViewBinding> : FragmentActivity() {
    protected lateinit var bd: TT
    protected val mContext: Context
    protected val mActivity: Activity

    init {
        mContext = this
        mActivity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bd.root)
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

    abstract fun getBinding(): ViewBinding
}