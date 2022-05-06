package com.yinp.tools.shap_view

import android.content.Context
import android.graphics.BitmapShader
import android.graphics.Color
import android.graphics.Shader
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.widget.FrameLayout
import com.yinp.tools.*
import com.yinp.tools.utils.ToolsUtils


/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.tools.shap_view
 * describe  :
 */
class ShapeFrameLayout : FrameLayout {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private var oneDrawable: Drawable? = null
    private var twoDrawable: Drawable? = null
    private var mState = 0

    /**
     * 四个圆角
     */
    private fun init(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.ShapeFrameLayout).apply {
            oneDrawable = getDrawable(R.styleable.ShapeFrameLayout_sflOneBg)
            twoDrawable = getDrawable(R.styleable.ShapeFrameLayout_sflTwoBg)
            val radius = getInteger(R.styleable.ShapeFrameLayout_sflRadius, 0)
            val leftTopRadius = getInteger(R.styleable.ShapeFrameLayout_sflLtRadius, 0).toFloat()
            val leftBottomRadius =
                getInteger(R.styleable.ShapeFrameLayout_sflLbRadius, 0).toFloat()
            val rightTopRadius = getInteger(R.styleable.ShapeFrameLayout_sflRtRadius, 0).toFloat()
            val rightBottomRadius =
                getInteger(R.styleable.ShapeFrameLayout_sflRbRadius, 0).toFloat()
            mState = getInt(R.styleable.ShapeFrameLayout_sflState, 0)
            recycle()
            if (mState != 0) {
                setOnClickListener {}
                mIsState = true
            }
            if (radius != 0) {
                initShape(radius)
            } else {
                initShape(leftTopRadius, rightTopRadius, leftBottomRadius, rightBottomRadius)
            }
        }
        fill(oneDrawable, twoDrawable)
    }

    private var mIsState = false

    private fun fill(oneD: Drawable?, twoD: Drawable?) {
        val a: Int
        val b: Int
        var one = when (oneD) {
            is BitmapDrawable -> {
                a = 0
                setBg(oneD)
            }
            is GradientDrawable -> {
                a = 1
                setBg(oneD)
            }
            is ColorDrawable -> {
                a = 2
                setBg(oneD.color)
            }
            else -> {
                a = 3
                null
            }
        }
        var two = when (twoD) {
            is BitmapDrawable -> {
                b = 0
                setBg(twoD)
            }
            is GradientDrawable -> {
                b = 1
                setBg(twoD)
            }
            is ColorDrawable -> {
                b = 2
                setBg(twoD.color)
            }
            else -> {
                b = 3
                null
            }
        }
        if (mIsState) {
            when {
                oneD != null && twoD != null -> {
                    initState(one, two)
                    setMBackGround(stateListDrawable)
                }
                oneD == null && twoD != null -> {
                    if (twoD is ColorDrawable)
                        one = setBg(Color.parseColor(getHexString((twoD).color)))
                    initState(one, two)
                    setMBackGround(stateListDrawable)
                }
                oneD != null && twoD == null -> {
                    if (oneD is ColorDrawable)
                        two = setBg(Color.parseColor(getHexString((oneD).color)))
                    initState(one, two)
                    setMBackGround(stateListDrawable)
                }
                else -> {
                    setBackgroundColor(Color.WHITE)
                }
            }
        } else {
            when {
                a == 3 && b == 3 -> {
                    setBackgroundColor(Color.WHITE)
                }
                a <= b -> {
                    setMBackGround(one)
                }
                else -> {
                    setMBackGround(two)
                }
            }
        }
    }


    private fun initState(drawable1: Drawable?, drawable2: Drawable?) {
        when (mState) {
            1 -> {
                addSate(drawable2, state_pressed)
            }
            2 -> {
                addSate(drawable2, state_selected)
            }
            3 -> {//state_pressed and state_selected
                addSate(drawable2, state_pressed)
                addSate(drawable2, state_selected)
            }
            4 -> {
                addSate(drawable2, state_checked)
            }
            5 -> {//state_pressed and state_checked
                addSate(drawable2, state_pressed)
                addSate(drawable2, state_checked)
            }
            6 -> {//state_selected and state_checked
                addSate(drawable2, state_selected)
                addSate(drawable2, state_checked)
            }
            8 -> {
                addSate(drawable2, state_focused)
            }
        }
        stateListDrawable.addState(intArrayOf(state_checked), drawable1)
    }

    private fun addSate(drawable: Drawable?, state: Int) {
        stateListDrawable.addState(intArrayOf(state), drawable)
    }

    private val stateListDrawable by lazy(LazyThreadSafetyMode.NONE) {
        StateListDrawable()
    }
    private var roundRectShape: RoundRectShape? = null

    fun setRadius(radius: Int) {
        initShape(radius)
    }

    fun setRadius(
        leftBottomRadius: Float,
        leftTopRadius: Float,
        rightTopRadius: Float,
        rightBottomRadius: Float
    ) {
        initShape(leftBottomRadius, leftTopRadius, rightTopRadius, rightBottomRadius)
    }

    /**
     * 动态设置,圆角必须设置在图片和颜色之前
     *
     * @param color
     */
    fun setColor(color: Int) {
        setColor(color, DEFAULT)
    }

    fun setColor(color: Int, @AndroidState state: Int) {
        this.mState = state
        if (state != 0) {
            setOnClickListener { }
            mIsState = true
        }
        if (mIsState) {
            val twoColor = getHexString(color)
            initState(setBg(color), setBg(Color.parseColor(twoColor)))
            setMBackGround(stateListDrawable)
        } else {
            setMBackGround(setBg(color))
        }
    }

    fun setDrawable(drawable: Drawable?) {
        when (drawable) {
            is BitmapDrawable -> {
                setMBackGround(setBg(drawable as BitmapDrawable?))
            }
            is GradientDrawable -> {
                setMBackGround(setBg(drawable as GradientDrawable?))
            }
            is ColorDrawable -> {
                setMBackGround(setBg(drawable.color))
            }
        }
    }

    /**
     * 所有圆角一样
     *
     * @param radius
     */
    private fun initShape(radius: Int) {
        var radius = radius
        if (radius != 0) {
            radius = radius.dpToPx()
            val outerRadii = floatArrayOf(
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat()
            )
            roundRectShape = RoundRectShape(outerRadii, null, null)
        }
    }

    /**
     * 单独设置圆角
     *
     * @param leftBottomRadius
     * @param leftTopRadius
     * @param rightTopRadius
     * @param rightBottomRadius
     */
    private fun initShape(
        leftTopRadius: Float,
        rightTopRadius: Float,
        leftBottomRadius: Float,
        rightBottomRadius: Float
    ) {
        if (leftBottomRadius != 0f || leftTopRadius != 0f || rightTopRadius != 0f || rightBottomRadius != 0f) {
            roundRectShape = RoundRectShape(
                mutableListOf(
                    leftTopRadius,
                    leftTopRadius,
                    rightTopRadius,
                    rightTopRadius,
                    rightBottomRadius,
                    rightBottomRadius,
                    leftBottomRadius,
                    leftBottomRadius
                ).map {
                    it.toFloat().dpToPx()
                }.toFloatArray(), null, null
            )
        }
    }

    /**
     * 颜色
     */
    private fun setBg(color: Int): Drawable {
        return if (roundRectShape == null) {
            ShapeDrawable()
        } else {
            ShapeDrawable(roundRectShape)
        }.apply {
            paint.color = color
            setMBackGround(this)
        }
    }

    /**
     * xml写的gradientDrawable
     */
    private fun setBg(drawable: GradientDrawable?): Drawable? {
        return when (drawable) {
            null -> null
            else -> {
                setMBackGround(drawable)
                drawable
            }
        }
    }

    /**
     * 设置的图片
     */
    fun setBitmapBg() {
        setBg(oneDrawable as BitmapDrawable?)
    }

    private fun setBg(drawable: BitmapDrawable?): Drawable? {
        return when (drawable) {
            null -> null
            else -> {
                val bitmapShader =
                    BitmapShader(drawable.bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                if (roundRectShape == null) {
                    ShapeDrawable()
                } else {
                    ShapeDrawable(roundRectShape)
                }.apply {
                    paint.shader = bitmapShader
                    setMBackGround(this)
                }
            }
        }
    }

    private fun setMBackGround(drawable: Drawable?) {
        background = drawable
    }

    /**
     * 默认改变70%的透明度
     * 透明的颜色没有用
     *
     * @param color
     * @return
     */
    private fun getHexString(color: Int): String {
        return when (color) {
            0, -1 -> "#e6e6e6"
            else -> {
                val oldColor =
                    color and -0x1000000 or (color and 0x00ff0000) or (color and 0x0000ff00) or (color and 0x000000ff)
                val oldC = Integer.toHexString(oldColor)
                when {
                    oldC.startsWith("00") || oldC == "ffffffff" -> "#e6e6e6"
                    else -> {
                        val s = "#"
                        val colorStr =
                            color and -0x4d000000 or (color and 0x00ff0000) or (color and 0x0000ff00) or (color and 0x000000ff)
                        s.plus(Integer.toHexString(colorStr))
                    }
                }
            }
        }
    }

}