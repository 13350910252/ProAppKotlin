package com.yinp.tools.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.yinp.tools.R
import com.yinp.tools.utils.ToolsUtils


/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.tools.view
 * describe  :
 */
class ShadowFrameLayout : FrameLayout {

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private var isPaddingLeft = false
    private var isPaddingTop = false
    private var isPaddingRight = false
    private var isPaddingBottom = false
    private var isWidgetBorder = false

    private var mPaddingLeft = 0
    private var mPaddingTop = 0
    private var mPaddingRight = 0
    private var mPaddingBottom = 0

    private var mShadowColor = 0
    private var mBackgroundColor = 0

    private var mShadowLimit = 0f
    private var mCornerRadius = 0f

    private val shadowPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }
    }
    private val bgPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = mBackgroundColor
        }
    }

    //初始化各个参数和画笔
    private fun init(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ShadowFrameLayout).apply {
            isPaddingLeft = getBoolean(R.styleable.ShadowFrameLayout_sl_padding_left, true)
            isPaddingTop = getBoolean(R.styleable.ShadowFrameLayout_sl_padding_top, true)
            isPaddingRight = getBoolean(R.styleable.ShadowFrameLayout_sl_padding_right, true)
            isPaddingBottom = getBoolean(R.styleable.ShadowFrameLayout_sl_padding_bottom, true)
            mShadowColor = getColor(
                R.styleable.ShadowFrameLayout_sl_shadow_color,
                resources.getColor(R.color.shadow_color)
            ) //必须要一个透明度
            mBackgroundColor = getColor(
                R.styleable.ShadowFrameLayout_sl_background_color,
                resources.getColor(R.color.white)
            )
            mShadowLimit = getDimension(R.styleable.ShadowFrameLayout_sl_shadow_width, 4f)
            mCornerRadius = getDimension(R.styleable.ShadowFrameLayout_sl_corner_radius, 0f)
            isWidgetBorder = getBoolean(R.styleable.ShadowFrameLayout_sl_widget_border, true)
            recycle()
        }
        mShadowLimit = ToolsUtils.dpToPx(mShadowLimit)
        mCornerRadius = ToolsUtils.dpToPx(mCornerRadius) //决定边角的圆角

        setPad()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h)
        }
    }

    private fun setBackgroundCompat(w: Int, h: Int) {
        val bitmap = createBitmap(w, h)
        val drawable = BitmapDrawable(context.resources, bitmap)
        background = drawable
    }

    private fun createBitmap(shadowWidth: Int, shadowHeight: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val shadowRectF = RectF(
            mShadowLimit,
            mShadowLimit,
            shadowWidth - mShadowLimit,
            shadowHeight - mShadowLimit
        )
        shadowPaint.color = Color.TRANSPARENT //设置透明
        if (!isInEditMode) {
            //四个参数：radius,阴影的扩散范围，为0就没有，越大越模糊；阴影平面dx和dy上下偏移量，阴影颜色
            shadowPaint.setShadowLayer(mShadowLimit, 0f, 0f, mShadowColor)
        }
        canvas.drawRoundRect(shadowRectF, mCornerRadius, mCornerRadius, shadowPaint)
        return bitmap
    }

    //设置边距来显示阴影
    private fun setPad() {
        mPaddingLeft = if (isPaddingLeft) {
            mShadowLimit.toInt()
        } else {
            0
        }
        mPaddingTop = if (isPaddingTop) {
            mShadowLimit.toInt()
        } else {
            0
        }
        mPaddingRight = if (isPaddingRight) {
            mShadowLimit.toInt()
        } else {
            0
        }
        mPaddingBottom = if (isPaddingBottom) {
            mShadowLimit.toInt()
        } else {
            0
        }
        setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom)
    }

    private val backgroundRectF = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isWidgetBorder) {
            backgroundRectF.left = mPaddingLeft.toFloat()
            backgroundRectF.top = mPaddingTop.toFloat()
            backgroundRectF.right = (width - mPaddingRight).toFloat()
            backgroundRectF.bottom = (height - mPaddingBottom).toFloat()
            val trueHeight = (backgroundRectF.bottom - backgroundRectF.top).toInt()
            if (mCornerRadius > trueHeight / 2) {
                //画圆角矩阵
                canvas.drawRoundRect(backgroundRectF, trueHeight / 2f, trueHeight / 2f, bgPaint)
            } else {
                canvas.drawRoundRect(backgroundRectF, mCornerRadius, mCornerRadius, bgPaint)
            }
        }
    }
}