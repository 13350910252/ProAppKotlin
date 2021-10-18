package com.yinp.tools.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.tools.view
 * describe  :
 */
class WuJiaoXingView : View {
    constructor(context: Context?) : super(context, null) {
        init()
    }

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

    private var paint: Paint? = null
    private var paint1: Paint? = null

    private fun init() {
        paint = Paint()
        paint!!.style = Paint.Style.STROKE
        paint!!.color = Color.YELLOW
        paint1 = Paint()
        paint1!!.style = Paint.Style.STROKE
        paint1!!.color = Color.RED
        paint1!!.strokeWidth = 8f
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
        centerY = (centerX * Math.tan(ANGLE / 3)).toFloat()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawXingXing(canvas)
        drawKuang(canvas)
    }

    private fun drawXingXing(canvas: Canvas) {
        val path = Path()
        path.moveTo(centerX, 0f)
        val x = (height * Math.tan(ANGLE / 6)).toFloat()
        path.lineTo(centerX - x, height.toFloat())
        path.lineTo(width.toFloat(), centerY)
        path.lineTo(0f, centerY)
        path.lineTo(centerX + x, height.toFloat())
        path.close()
        canvas.drawPath(path, paint1!!)
    }

    private fun drawKuang(canvas: Canvas) {}
}