package com.yinp.tools.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import com.yinp.tools.R

/**
 * @author   :yinpeng
 * date      :2022/1/12
 * package   :com.yinp.tools.view
 * describe  :
 */
class ToastUtil(val context: Context) : Toast(context) {
    /**
     * @hide
     */
    @IntDef(value = [LENGTH_SHORT, LENGTH_LONG])
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Duration

    companion object {
        private fun cancelToast() {
            mToastUtil?.cancel()
        }

        private var mToastUtil: ToastUtil? = null
        private lateinit var mContext: Context

        fun initToast(context: Context, @StringRes resId: Int) {
            initToast(context, context.resources.getText(resId))
        }

        fun initToast(context: Context, text: CharSequence?) {
            initToast(context, text, LENGTH_SHORT)
        }

        fun initToast(context: Context, text: CharSequence?, @Duration time: Int) {
            //第二次点击及以后，它会先取消上一次的Toast, 然后show本次的Toast。
            mContext = context
            cancelToast()
            val mView: View = LayoutInflater.from(context).inflate(R.layout.layout_toast, null)
            val tvContent = mView.findViewById<TextView>(R.id.tv_content)
            tvContent.text = text

            mToastUtil = ToastUtil(context).apply {
                view = mView
                setGravity(Gravity.BOTTOM, 0, 70)
                duration = time
            }.also {
                it.show()
            }
        }

        fun initStatusToast(context: Context, resId: Int, status: Boolean) {
            initStatusToast(
                context,
                context.resources.getText(resId),
                LENGTH_SHORT,
                status
            )
        }

        fun initStatusToast(context: Context, text: CharSequence, status: Boolean) {
            initStatusToast(context, text, LENGTH_SHORT, status)
        }

        fun initStatusToast(
            context: Context,
            text: CharSequence,
            @Duration time: Int,
            status: Boolean
        ) {
            //第二次点击及以后，它会先取消上一次的Toast, 然后show本次的Toast。
            mContext = context
            cancelToast()
            val mView: View = LayoutInflater.from(context).inflate(R.layout.layout_toast, null)
            val tvContent = mView.findViewById<TextView>(R.id.tv_content)
            val ivStatus = mView.findViewById<ImageView>(R.id.iv_status)
            ivStatus.visibility = View.VISIBLE
            if (status) {
                ivStatus.setBackgroundResource(R.mipmap.success_icon)
            } else {
                ivStatus.setBackgroundResource(R.mipmap.fail_icon)
            }
            tvContent.text = text

            mToastUtil = ToastUtil(context).apply {
                view = mView
                setGravity(Gravity.CENTER, 0, 0)
                duration = time
            }.also {
                it.show()
            }
        }
    }

    override fun cancel() {
        super.cancel()
    }

    override fun show() {
        super.show()
    }
}