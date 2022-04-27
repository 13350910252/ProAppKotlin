package com.yinp.tools.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.yinp.tools.R

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.tools.view
 * describe  :
 */
class ColorCircleLinearLayout : LinearLayout {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private val circlePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            style = Paint.Style.FILL
        }
    }
    private val radius by lazy {
        if (height > width) {
            width / 2
        } else {
            height / 2
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ColorCircleLinearLayout).apply {
            circlePaint.color = getColor(R.styleable.ColorCircleLinearLayout_ccll_bgColor, 0)
            recycle()
            background = null
        }
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), circlePaint)
    }

    /**
     * 动态改变颜色
     *
     * @param color
     */
    fun changeColor(color: Int) {
        circlePaint.color = color
        postInvalidate()
    }

    fun changeColor(context: Context?, @ColorRes color: Int) {
        circlePaint.color = ContextCompat.getColor(context!!, color)
        postInvalidate()
    }
}