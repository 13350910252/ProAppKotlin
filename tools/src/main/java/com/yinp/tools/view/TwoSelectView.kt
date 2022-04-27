package com.yinp.tools.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.yinp.tools.utils.ToolsUtils

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.tools.view
 * describe  :
 */
class TwoSelectView : View {

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

    private val paint by lazy {
        Paint().apply {
            color = Color.RED
        }
    }
    private val paintTextOne by lazy {
        Paint().apply {
            color = Color.WHITE
            textSize = ToolsUtils.dpToPx(20f)
        }
    }
    private val paintTextTwo by lazy {
        Paint().apply {
            color = Color.WHITE
            textSize = ToolsUtils.dpToPx(20f)
        }
    }
    private var textHeight = 0f
    private var radius = 0f
    private val addWidth = 10f

    private fun init(context: Context, attrs: AttributeSet?) {
        background = null

        val textOneWidth = paintTextOne.measureText("极光登录")
        val fontMetrics = paintTextOne.fontMetrics
        textHeight = fontMetrics.descent - fontMetrics.ascent
        radius = if (textOneWidth - textHeight > 0) textOneWidth / 2 else textHeight / 2
        radius += addWidth
        Log.d("abcd", "init: $radius")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension((radius * 3).toInt(), (radius * 4).toInt())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (event.y < radius * 2) {
                if (event.x > radius) {
                    if (cLickListener != null) {
                        cLickListener!!.click(false)
                    }
                }
            } else {
                if (event.x < radius * 2) {
                    if (cLickListener != null) {
                        cLickListener!!.click(true)
                    }
                }
            }
        }
        return false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(radius, radius * 3, radius, paint)
        canvas.drawText("极光登录", addWidth, radius * 3 + textHeight / 4, paintTextOne)
        canvas.drawCircle(radius * 2, radius, radius, paint)
        canvas.drawText("分享", addWidth + radius, radius + textHeight / 4, paintTextTwo)
    }

    abstract class CLickListener {
        abstract fun click(isLeft: Boolean)
    }

    var cLickListener: CLickListener? = null

    fun setcLickListener(cLickListener: CLickListener?) {
        this.cLickListener = cLickListener
    }
}