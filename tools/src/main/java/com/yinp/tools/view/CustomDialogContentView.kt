package com.yinp.tools.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.tools.view
 * describe  :
 */
class CustomDialogContentView(context: Context) : RelativeLayout(context) {

    /**
     * 对事件进行拦截，不会穿透
     * @param event
     * @return
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }
}