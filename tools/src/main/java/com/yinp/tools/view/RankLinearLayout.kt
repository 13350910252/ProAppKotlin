package com.yinp.tools.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.IdRes
import com.yinp.tools.R

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.tools.view
 * describe  :
 */
class RankLinearLayout : LinearLayout {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initBase(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initBase(context, attrs)
    }

    private var paint: Paint? = null
    private var percent = 0f
    private var percentWidth = 0f
    private var duration = 0
    private var bgColor = 0
    private var isStart = false

    private fun initBase(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RankLinearLayout)
        bgColor = array.getColor(R.styleable.RankLinearLayout_bgColor, 0)
        percentWidth = array.getFloat(R.styleable.RankLinearLayout_schedule, 0f)
        duration = array.getInteger(R.styleable.RankLinearLayout_duration, 600)
        isStart = array.getBoolean(R.styleable.RankLinearLayout_isStart, false)
        array.recycle()
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = bgColor
        paint!!.style = Paint.Style.FILL
        setWillNotDraw(false)
        if (isStart) {
            setAnimator()
        }
    }

    fun setDuration(duration: Int): RankLinearLayout {
        this.duration = duration
        return this
    }

    fun setBgColor(@IdRes bgColor: Int): RankLinearLayout {
        this.bgColor = bgColor
        return this
    }

    fun setPercentWidth(percentWidth: Float): RankLinearLayout {
        this.percentWidth = percentWidth
        return this
    }

    fun start() {
        setAnimator()
    }

    private fun setAnimator() {
        if (percentWidth == 0f) {
            return
        }
        val valueAnimator = ValueAnimator.ofFloat(0f, percentWidth)
        valueAnimator.addUpdateListener { animation: ValueAnimator ->
            percent = animation.animatedValue as Float
            postInvalidate()
        }
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(0f, 0f, right * percent, measuredHeight.toFloat(), paint!!)
    }
}