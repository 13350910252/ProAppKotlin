package com.yinp.tools.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yinp.tools.R

/**
 * @author   :yinpeng
 * date      :2021/10/28
 * package   :com.yinp.myapplication.show
 * describe  :
 */
class LoadingUtils(val mManager: FragmentManager, val tag: String = "") : DefaultLifecycleObserver {
    private lateinit var mDialogFragment: LoadingDialog
    private val dialogList = mutableListOf<LoadingDialog>()

    fun show(text: String, tag: String = "") {
        mDialogFragment = LoadingDialog(text)
        dialogList.add(mDialogFragment)
        mDialogFragment.show(mManager, tag)
    }

    fun close(tag: String = "") {
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

    override fun onStop(owner: LifecycleOwner) {
        closeAll()
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