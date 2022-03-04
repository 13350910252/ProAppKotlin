package com.yinp.tools.utils

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.StringRes

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
        fun initToast(context: Context, @StringRes resId: Int) {
            initToast(context, Resources.getSystem().getText(resId))
        }

        fun initToast(context: Context, text: CharSequence?) {
            initToast(context, text, LENGTH_SHORT)
        }

        fun initToast(context: Context, text: CharSequence?, @Duration time: Int) {
            Toast(context.applicationContext).apply {
                setGravity(Gravity.BOTTOM, 0, 70)
                duration = time
                setText(text)
            }.show()
        }
    }
}