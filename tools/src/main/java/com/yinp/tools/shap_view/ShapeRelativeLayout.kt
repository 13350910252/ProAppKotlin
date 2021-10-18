package com.yinp.tools.shap_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.BitmapShader
import android.graphics.Color
import android.graphics.Shader
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.yinp.tools.*
import com.yinp.tools.utils.ToolsUtils


/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.tools.shap_view
 * describe  :
 */
class ShapeRelativeLayout : RelativeLayout {
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

    private var oneDrawable: Drawable? = null
    private var twoDrawable: Drawable? = null
    private var state = 0

    private var radius = 0
    private var leftBottomRadius: Int = 0
    private var leftTopRadius: Int = 0
    private var rightTopRadius: Int = 0
    private var rightBottomRadius: Int = 0


    private fun init(context: Context, attrs: AttributeSet?) {
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeRelativeLayout)
        oneDrawable = ta.getDrawable(R.styleable.ShapeRelativeLayout_oneBg)
        twoDrawable = ta.getDrawable(R.styleable.ShapeRelativeLayout_twoBg)
        radius = ta.getInteger(R.styleable.ShapeRelativeLayout_radius, 0)
        leftTopRadius = ta.getInteger(R.styleable.ShapeRelativeLayout_lt_radius, 0)
        leftBottomRadius = ta.getInteger(R.styleable.ShapeRelativeLayout_lb_radius, 0)
        rightTopRadius = ta.getInteger(R.styleable.ShapeRelativeLayout_rt_radius, 0)
        rightBottomRadius = ta.getInteger(R.styleable.ShapeRelativeLayout_rb_radius, 0)
        state = ta.getInt(R.styleable.ShapeRelativeLayout_state, 0)
        ta.recycle()
        if (state != 0) {
            setOnClickListener { }
            isState = true
        }
        if (radius != 0) {
            initShape(radius)
        } else {
            initShape(leftTopRadius, rightTopRadius, leftBottomRadius, rightBottomRadius)
        }
        fill(oneDrawable, twoDrawable)
    }

    private var isState = false

    private fun fill(oneD: Drawable?, twoD: Drawable?) {
        if (oneD != null && twoD != null) {
            if (oneD is BitmapDrawable && twoD is BitmapDrawable) {
                if (isState) {
                    initState(setBg(oneD as BitmapDrawable?), setBg(twoD as BitmapDrawable?))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg(oneD as BitmapDrawable?))
                }
            } else if (oneD is BitmapDrawable && twoD is GradientDrawable) {
                if (isState) {
                    initState(setBg(oneD as BitmapDrawable?), setBg(twoD as GradientDrawable?))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg(oneD as BitmapDrawable?))
                }
            } else if (oneD is BitmapDrawable && twoD is ColorDrawable) {
                if (isState) {
                    initState(setBg(oneD as BitmapDrawable?), setBg(twoD.color))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg(oneD as BitmapDrawable?))
                }
            } else if (twoD is BitmapDrawable && oneD is GradientDrawable) {
                if (isState) {
                    initState(setBg(twoD as BitmapDrawable?), setBg(oneD as GradientDrawable?))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg(twoD as BitmapDrawable?))
                }
            } else if (twoD is BitmapDrawable && oneD is ColorDrawable) {
                if (isState) {
                    initState(setBg(twoD as BitmapDrawable?), setBg(oneD.color))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg(twoD as BitmapDrawable?))
                }
            } else if (oneD is GradientDrawable && twoD is GradientDrawable) {
                if (isState) {
                    initState(setBg(oneD as GradientDrawable?), setBg(twoD as GradientDrawable?))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg(oneD as GradientDrawable?))
                }
            } else if (oneD is GradientDrawable && twoD is ColorDrawable) {
                if (isState) {
                    initState(setBg(oneD as GradientDrawable?), setBg(twoD.color))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg(oneD as GradientDrawable?))
                }
            } else if (twoD is GradientDrawable && oneD is ColorDrawable) {
                if (isState) {
                    initState(setBg(twoD as GradientDrawable?), setBg(oneD.color))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg(twoD as GradientDrawable?))
                }
            } else if (oneD is ColorDrawable && twoD is ColorDrawable) {
                if (isState) {
                    initState(setBg(oneD.color), setBg(twoD.color))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg(oneD.color))
                }
            }
        } else if (oneD != null) { //在一种颜色的情况下，自动设置75%不透明度的颜色
            if (oneD is BitmapDrawable) {
                setMBackGround(setBg(oneD as BitmapDrawable?))
            } else if (oneD is GradientDrawable) {
                setMBackGround(setBg(oneD as GradientDrawable?))
            } else {
                if (isState) {
                    val twoColor = getHexString((oneD as ColorDrawable).color)
                    initState(setBg(oneD.color), setBg(Color.parseColor(twoColor)))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg((oneD as ColorDrawable).color))
                }
            }
        } else if (twoD != null) {
            if (twoD is BitmapDrawable) {
                setMBackGround(setBg(twoD as BitmapDrawable?))
            } else if (twoD is GradientDrawable) {
                setMBackGround(setBg(twoD as GradientDrawable?))
            } else {
                if (isState) {
                    val twoColor = getHexString((twoD as ColorDrawable).color)
                    initState(setBg(twoD.color), setBg(Color.parseColor(twoColor)))
                    setMBackGround(stateListDrawable)
                } else {
                    setMBackGround(setBg((twoD as ColorDrawable).color))
                }
            }
        } else {
            setBackgroundColor(Color.WHITE)
        }
    }

    private fun initState(drawable1: Drawable?, drawable2: Drawable?) {
        stateListDrawable = StateListDrawable()
        when (state) {
            1 -> {
                stateListDrawable!!.addState(intArrayOf(-state_pressed), drawable1)
                stateListDrawable!!.addState(intArrayOf(state_pressed), drawable2)
            }
            2 -> {
                stateListDrawable!!.addState(intArrayOf(-state_selected), drawable1)
                stateListDrawable!!.addState(intArrayOf(state_selected), drawable2)
            }
            3 -> {
                stateListDrawable!!.addState(intArrayOf(-state_checked), drawable1)
                stateListDrawable!!.addState(intArrayOf(state_checked), drawable2)
            }
            4 -> {
                stateListDrawable!!.addState(intArrayOf(-state_focused), drawable1)
                stateListDrawable!!.addState(intArrayOf(state_focused), drawable2)
            }
        }
    }

    private var stateListDrawable: StateListDrawable? = null
    private var roundRectShape: RoundRectShape? = null

    private var color = 0
    private var drawable: Drawable? = null


    fun setRadius(radius: Int) {
        this.radius = radius
        initShape(radius)
    }

    fun setRadius(
        leftBottomRadius: Int,
        leftTopRadius: Int,
        rightTopRadius: Int,
        rightBottomRadius: Int
    ) {
        this.leftBottomRadius = leftBottomRadius
        this.leftTopRadius = leftTopRadius
        this.rightTopRadius = rightTopRadius
        this.rightBottomRadius = rightBottomRadius
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
        this.color = color
        this.state = state
        if (state != 0) {
            setOnClickListener { v: View? -> }
            isState = true
        }
        if (isState) {
            val twoColor = getHexString(color)
            initState(setBg(color), setBg(Color.parseColor(twoColor)))
            setMBackGround(stateListDrawable)
        } else {
            setMBackGround(setBg(color))
        }
    }

    fun setDrawable(drawable: Drawable?) {
        this.drawable = drawable
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
            radius = ToolsUtils.dpToPx(context, radius)
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
        leftTopRadius: Int,
        rightTopRadius: Int,
        leftBottomRadius: Int,
        rightBottomRadius: Int
    ) {
        if (leftBottomRadius != 0 || leftTopRadius != 0 || rightTopRadius != 0 || rightBottomRadius != 0) {
            this.leftBottomRadius = ToolsUtils.dpToPx(context, leftBottomRadius)
            this.leftTopRadius = ToolsUtils.dpToPx(context, leftTopRadius)
            this.rightTopRadius = ToolsUtils.dpToPx(context, rightTopRadius)
            this.rightBottomRadius = ToolsUtils.dpToPx(context, rightBottomRadius)
            val outerRadii = floatArrayOf(
                leftTopRadius.toFloat(),
                leftTopRadius.toFloat(),
                rightTopRadius.toFloat(),
                rightTopRadius.toFloat(),
                rightBottomRadius.toFloat(),
                rightBottomRadius.toFloat(),
                leftBottomRadius.toFloat(),
                leftBottomRadius.toFloat()
            )
            roundRectShape = RoundRectShape(outerRadii, null, null)
        }
    }

    /**
     * 颜色
     */
    fun setColorBg() {
        setBg(color)
    }

    private fun setBg(color: Int): Drawable {
        val shapeDrawable: ShapeDrawable = if (roundRectShape == null) {
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
    fun setGradientBg() {
        setBg(oneDrawable as GradientDrawable?)
    }

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
        s += Integer.toHexString(colorStr)
        return s
    }
}