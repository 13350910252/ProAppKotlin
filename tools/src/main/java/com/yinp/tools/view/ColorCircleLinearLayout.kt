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
class ColorCircleLinearLayout: LinearLayout {
    constructor(context: Context, attrs: AttributeSet?):super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private var circlePaint: Paint? = null
    private var radius = 0

    private fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ColorCircleLinearLayout)
        val bgColor = ta.getColor(R.styleable.ColorCircleLinearLayout_ccll_bgColor, 0)
        ta.recycle()
        background = null
        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        circlePaint!!.color = bgColor
        circlePaint!!.style = Paint.Style.FILL
        setWillNotDraw(false)
    }

    private var isFirst = true

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isFirst) {
            isFirst = false
            val height = height
            val width = width
            radius = if (height > width) {
                width / 2
            } else {
                height / 2
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), circlePaint!!)
    }

    /**
     * 动态改变颜色
     *
     * @param color
     */
    fun changeColor(color: Int) {
        circlePaint!!.color = color
        postInvalidate()
    }

    fun changeColor(context: Context?, @ColorRes color: Int) {
        circlePaint!!.color = ContextCompat.getColor(context!!, color)
        postInvalidate()
    }
}