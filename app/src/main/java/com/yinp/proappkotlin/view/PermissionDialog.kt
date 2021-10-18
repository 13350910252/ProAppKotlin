package com.yinp.proappkotlin.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.hjq.permissions.XXPermissions
import com.yinp.proappkotlin.R

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.view
 * describe  :
 */
class PermissionDialog : Dialog {
    private var context: Activity? = null
    private var title: String? = null
    private var permissions: List<String>? = null

    constructor(activity: Activity, title: String?, permissions: List<String>?) : super(
        activity,
        R.style.NormalDialogStyle
    ) {
        context = activity
        this.title = title
        this.permissions = permissions
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        @SuppressLint("InflateParams") val view: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_permission, null)
        val tvLeft = view.findViewById<TextView>(R.id.dialog_left)
        val tvRight = view.findViewById<TextView>(R.id.dialog_right)
        val tvTitle = view.findViewById<TextView>(R.id.dialog_content)
        setContentView(view)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        tvTitle.text = title
        tvLeft.setOnClickListener { v: View? -> dismiss() }
        tvRight.setOnClickListener { v: View? ->
            XXPermissions.startPermissionActivity(context, permissions)
            dismiss()
        }
        val displayMetrics = context!!.resources.displayMetrics
        val params = window?.attributes
        params?.width = displayMetrics.widthPixels * 8 / 10
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = params
    }
}