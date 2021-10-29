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
    private lateinit var dialogFrament: LoadingDialog
    private val dialogList = ArrayList<LoadingDialog>()

    fun show(manager: FragmentManager, text: String) {
        show(manager, text, "")
    }

    fun show(manager: FragmentManager, text: String, tag: String) {
        dialogFrament = LoadingDialog(text)
        dialogList.add(dialogFrament)
        dialogFrament.show(manager, tag)
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
            val window = dialog?.window
            window?.let {
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //解决弹窗有空的问题
                it.decorView.setBackgroundResource(R.drawable.bg_transparent)
                val layoutParams = window.attributes
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                window.setGravity(Gravity.CENTER)
                window.attributes = layoutParams
            }

        }
    }
}