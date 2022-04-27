package com.yinp.tools.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.tan

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.tools.view
 * describe  :
 */
class WuJiaoXingView : View {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private val paint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            color = Color.YELLOW
        }
    }
    private val paint1 by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            color = Color.RED
            strokeWidth = 8f
        }
    }

    private fun init() {
    }

    private var centerX = 0f
    private var centerY = 0f
    private var mWidth = 0
    private var mHeight = 0
    private val ANGLE = 2 * Math.PI / 360 * 108 //108度


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w
        mHeight = h
        centerX = (w / 2).toFloat()
        centerY = (centerX * tan(ANGLE / 3)).toFloat()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawXingXing(canvas)
        drawKuang(canvas)
    }

    private fun drawXingXing(canvas: Canvas) {
        canvas.drawPath(Path().apply {
            moveTo(centerX, 0f)
            val x = (height * tan(ANGLE / 6)).toFloat()
            lineTo(centerX - x, height.toFloat())
            lineTo(width.toFloat(), centerY)
            lineTo(0f, centerY)
            lineTo(centerX + x, height.toFloat())
            close()
        }, paint1)
    }

    private fun drawKuang(canvas: Canvas) {}
}