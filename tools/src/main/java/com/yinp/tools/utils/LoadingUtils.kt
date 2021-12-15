package com.yinp.tools.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.yinp.tools.R

/**
 * @author   :yinpeng
 * date      :2021/10/28
 * package   :com.yinp.myapplication.show
 * describe  :
 */
class LoadingUtils {
    private lateinit var dialogFragment: LoadingDialog
    private val dialogList = mutableListOf<LoadingDialog>()

    fun show(manager: FragmentManager, text: String) {
        show(manager, text, "")
    }

    fun show(manager: FragmentManager, text: String, tag: String) {
        dialogFragment = LoadingDialog(text)
        dialogList.add(dialogFragment)
        dialogFragment.show(manager, tag)
    }

    fun close() {
        close("")
    }

    fun close(tag: String) {
        for (item in dialogList) {
            if (item.tag == tag) {
                item.dismiss()
            }
        }
    }

    fun closeAll() {
        for (item in dialogList) {
            item.dismiss()
        }
        dialogList.clear()
    }

    class LoadingDialog(var text: String) : DialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val view = inflater.inflate(R.layout.dialog_loading, container, false)
            val tipTextView = view.findViewById<TextView>(R.id.tipTextView)
            tipTextView.text = text
            return view
        }

        override fun onResume() {
            super.onResume()
            dialog?.window?.run {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //解决弹窗有空的问题
                decorView.setBackgroundResource(R.drawable.bg_transparent)
                setGravity(Gravity.CENTER)
                attributes = attributes.apply {
                    width = WindowManager.LayoutParams.MATCH_PARENT
                    height = WindowManager.LayoutParams.WRAP_CONTENT
                }
            }

        }
    }
}