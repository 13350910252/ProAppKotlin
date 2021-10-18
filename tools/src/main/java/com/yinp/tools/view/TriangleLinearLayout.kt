package com.yinp.tools.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.LinearLayout
import com.yinp.tools.R
import com.yinp.tools.utils.ToolsUtils

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.tools.view
 * describe  :
 */
class TriangleLinearLayout : LinearLayout {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        init(context, attrs)
    }

    val paint1 by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            color = contentColor
            style = Paint.Style.FILL
        }
    } //画类容

    val paint2 by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            color = triangleColor
            style = Paint.Style.FILL
        }
    }//画三角形

    val path by lazy {
        Path()
    }

    private var triangleWidth = 0
    private var triangleHeight: Int = 0 //三角形的宽高

    private var contentColor = 0
    private var triangleColor: Int = 0
    private var contentCorners = 0 //圆角

    private var trianglePosition = 0 //三角形所处位置

    private var distance = 0//跟着三角形位置来的


    private var isFirst = false //只加载一次避免多次重复加载


    private fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TriangleLinearLayout)
        triangleWidth = ta.getInteger(R.styleable.TriangleLinearLayout_triangle_width, 8)
        triangleHeight = ta.getInteger(R.styleable.TriangleLinearLayout_triangle_height, 8)
        triangleColor = ta.getColor(R.styleable.TriangleLinearLayout_triangle_color, 0)
        trianglePosition = ta.getInteger(R.styleable.TriangleLinearLayout_triangle_position, 1)
        distance = ta.getInteger(R.styleable.TriangleLinearLayout_distance, 0)
        contentColor = ta.getColor(R.styleable.TriangleLinearLayout_content_color, 0)
        contentCorners = ta.getInteger(R.styleable.TriangleLinearLayout_content_corners, 0)
        ta.recycle()
        triangleWidth = ToolsUtils.dpToPx(context, triangleWidth)
        triangleHeight = ToolsUtils.dpToPx(context, triangleHeight) as Int
        contentCorners = ToolsUtils.dpToPx(context, contentCorners)
        distance = ToolsUtils.dpToPx(context, distance)
        if (triangleColor == 0) {
            triangleColor = contentColor
        }
        if (contentColor == 0) {
            contentColor = triangleColor
        }
        setPadding(0, triangleHeight, 0, 0)
        setWillNotDraw(false)
        setBackgroundResource(R.color.transparent)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (!isFirst) {
            init2()
            isFirst = true
        }
    }

    private fun init2() {
        when (trianglePosition) {
            1 -> editX(true)
            2 -> editX(false)
        }
    }

    //    在x轴上变化
    private fun editX(plus: Boolean) {
        if (plus) {
            path.moveTo(distance.toFloat(), triangleHeight.toFloat())
            path.lineTo((distance + triangleWidth / 2).toFloat(), 0f)
            path.lineTo((distance + triangleWidth).toFloat(), triangleHeight.toFloat())
            path.close()
        } else {
            path.moveTo((width - distance).toFloat(), triangleHeight.toFloat())
            path.lineTo((width - distance - triangleWidth / 2).toFloat(), 0f)
            path.lineTo((width - distance - triangleWidth).toFloat(), triangleHeight.toFloat())
            path.close()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint2)
        canvas.drawRoundRect(
            0f,
            triangleHeight.toFloat(),
            width.toFloat(),
            height.toFloat(),
            contentCorners.toFloat(),
            contentCorners.toFloat(),
            paint2
        )
    }
}