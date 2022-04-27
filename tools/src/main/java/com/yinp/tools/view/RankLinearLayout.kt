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

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = bgColor
            style = Paint.Style.FILL
        }
    }
    private var percent = 0f
    private var percentWidth = 0f
    private var duration = 0
    private var bgColor = 0
    private var isStart = false

    private fun initBase(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.RankLinearLayout).apply {
            bgColor = getColor(R.styleable.RankLinearLayout_rllBgColor, 0)
            percentWidth = getFloat(R.styleable.RankLinearLayout_rllSchedule, 0f)
            duration = getInteger(R.styleable.RankLinearLayout_rllDuration, 600)
            isStart = getBoolean(R.styleable.RankLinearLayout_rllIsStart, false)
            recycle()
        }
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
        canvas.drawRect(0f, 0f, right * percent, measuredHeight.toFloat(), paint)
    }
}