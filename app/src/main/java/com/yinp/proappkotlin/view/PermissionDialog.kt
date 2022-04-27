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
class PermissionDialog(
    activity: Activity,
    private var title: String?,
    private var permissions: List<String>?
) : Dialog(
    activity,
    R.style.NormalDialogStyle
) {
    private var context: Activity? = activity

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
        tvLeft.setOnClickListener { dismiss() }
        tvRight.setOnClickListener {
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