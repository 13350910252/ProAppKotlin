package com.yinp.tools.shap_view

import android.content.Context
import android.graphics.BitmapShader
import android.graphics.Color
import android.graphics.Shader
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.widget.LinearLayout
import com.yinp.tools.*
import com.yinp.tools.utils.ToolsUtils


/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.tools.shap_view
 * describe  :
 */

class ShapeLinearLayout : LinearLayout {
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
        context.obtainStyledAttributes(attrs, R.styleable.ShapeLinearLayout).apply {
            oneDrawable = getDrawable(R.styleable.ShapeLinearLayout_oneBg)
            twoDrawable = getDrawable(R.styleable.ShapeLinearLayout_twoBg)
            val radius = getInteger(R.styleable.ShapeLinearLayout_radius, 0)
            val leftTopRadius = getInteger(R.styleable.ShapeLinearLayout_lt_radius, 0).toFloat()
            val leftBottomRadius = getInteger(R.styleable.ShapeLinearLayout_lb_radius, 0).toFloat()
            val rightTopRadius = getInteger(R.styleable.ShapeLinearLayout_rt_radius, 0).toFloat()
            val rightBottomRadius = getInteger(R.styleable.ShapeLinearLayout_rb_radius, 0).toFloat()
            mState = getInt(R.styleable.ShapeLinearLayout_state, 0)
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
                stateListDrawable.addState(intArrayOf(-state_pressed), drawable1)
                stateListDrawable.addState(intArrayOf(state_pressed), drawable2)
            }
            2 -> {
                stateListDrawable.addState(intArrayOf(-state_selected), drawable1)
                stateListDrawable.addState(intArrayOf(state_selected), drawable2)
            }
            3 -> {
                stateListDrawable.addState(intArrayOf(-state_checked), drawable1)
                stateListDrawable.addState(intArrayOf(state_checked), drawable2)
            }
            4 -> {
                stateListDrawable.addState(intArrayOf(-state_focused), drawable1)
                stateListDrawable.addState(intArrayOf(state_focused), drawable2)
            }
        }
    }

    private val stateListDrawable by lazy(MNONE) {
        StateListDrawable()
    }
    private var roundRectShape: RoundRectShape? = null

    //所有圆角
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
        setColor(color, mState)
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
        if (radius != 0) {
            roundRectShape =
                RoundRectShape(
                    List(8) { ToolsUtils.dpToPx(radius.toFloat()) }.toFloatArray(),
                    null,
                    null
                )
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
                ).map { ToolsUtils.dpToPx(it) }.toFloatArray(), null, null
            )
        }
    }

    /**
     * 颜色
     */
    private fun setBg(color: Int): Drawable {
        val shapeDrawable = if (roundRectShape == null) {
            ShapeDrawable()
        } else {
            ShapeDrawable(roundRectShape)
        }
        shapeDrawable.paint.color = color
        setMBackGround(shapeDrawable)
        return shapeDrawable
    }

    /**
     * xml写的gradientDrawable
     */
    private fun setBg(drawable: GradientDrawable?): Drawable? {
        if (drawable == null) {
            return null
        }
        setMBackGround(drawable)
        return drawable
    }

    /**
     * 设置的图片
     */
    fun setBitmapBg() {
        setBg(oneDrawable as BitmapDrawable?)
    }

    private fun setBg(drawable: BitmapDrawable?): Drawable? {
        if (drawable == null) {
            return null
        }
        val bitmapShader =
            BitmapShader(drawable.bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val shapeDrawable: ShapeDrawable = if (roundRectShape == null) {
            ShapeDrawable()
        } else {
            ShapeDrawable(roundRectShape)
        }
        shapeDrawable.paint.shader = bitmapShader
        setMBackGround(shapeDrawable)
        return shapeDrawable
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
        if (color == 0 || color == -1) {
            return "#e6e6e6"
        }
        val oldColor =
            color and -0x1000000 or (color and 0x00ff0000) or (color and 0x0000ff00) or (color and 0x000000ff)
        val oldC = Integer.toHexString(oldColor)
        if (oldC.startsWith("00") || oldC == "ffffffff") {
            return "#e6e6e6"
        }
        var s = "#"
        val colorStr =
            color and -0x4d000000 or (color and 0x00ff0000) or (color and 0x0000ff00) or (color and 0x000000ff)
        return s.plus(Integer.toHexString(colorStr))
    }
}