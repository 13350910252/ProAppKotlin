package com.yinp.tools.view

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.yinp.tools.R

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.tools.view
 * describe  :
 */
class CustomUserAvatar : AppCompatImageView {

    private val mPaintText by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val mPaintBackground by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val mRect by lazy {
        Rect()
    }

    private var mUserName: String? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制发光效果
        val color = ContextCompat.getColor(context, R.color.white)
        mPaintBackground.color = color
        mPaintBackground.style = Paint.Style.FILL
        mPaintBackground.maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID)
        canvas.drawCircle(
            (width / 2).toFloat(), (width / 2).toFloat(), ((width - 20) / 2).toFloat(),
            mPaintBackground
        )
        // 设置文本大小
        mPaintText.textSize = (width / 3).toFloat()
        // 设置文本颜色跟随应用主题颜色
        mPaintText.color = ContextCompat.getColor(context, R.color.ff4d4d)
        // 设置画笔粗细
        mPaintText.strokeWidth = 5f
        // 设置阴影半径
        mPaintText.setShadowLayer(5f, 5f, 5f, ContextCompat.getColor(context, R.color.black))
        // 绘制文字的最小矩形
        mPaintText.getTextBounds(mUserName, 0, 1, mRect)
        val fontMetricsInt = mPaintText.fontMetricsInt
        // baseLine上面是负值，下面是正值
        // 所以getHeight()/2-fontMetricsInt.descent 将文本的bottom线抬高至控件的1/2处
        // + (fontMetricsInt.bottom - fontMetricsInt.top) / 2：(fontMetricsInt.bottom - fontMetricsInt.top) 文本的辅助线（top+bottom)/2就是文本的中位线（我是这样理解的）恰好在控件中位线处
        val baseLine =
            height / 2 - fontMetricsInt.descent + (fontMetricsInt.bottom - fontMetricsInt.top) / 2
        // 水平居中
        mPaintText.textAlign = Paint.Align.CENTER
        mUserName?.let { canvas.drawText(it, (width / 2).toFloat(), baseLine.toFloat(), mPaintText) }
    }


    /**
     * 判断一个字符是否是中文
     */
    fun isChineseChar(c: Char): Boolean {
        // 根据字节码判断
        return c.toInt() in 0x4E00..0x9FA5
    }

    /**
     * 判断一个字符串是否含有中文
     *
     * @param str
     * @return
     */
    fun isChineseString(str: String?): Boolean {
        if (str == null) {
            return false
        }
        for (c in str.toCharArray()) {
            if (isChineseChar(c)) {
                return true
            }
        }
        return false
    }

    /**
     * 设置显示的名字
     *
     * @param userName
     */
    fun setUserName(userName: String) {
        // 中文名字取后两个
        if (isChineseString(userName)) {
            mUserName = if (userName.length > 2) {
                userName.substring(userName.length - 2)
            } else {
                userName
            }
        } else {
            // 非中文名字取第一个
            if (userName.length > 1) {
                mUserName = userName.substring(0, 1)
                mUserName = mUserName!!.toUpperCase()
            } else {
                mUserName = userName
                mUserName = mUserName!!.toUpperCase()
            }
        }
        invalidate()
    }
}