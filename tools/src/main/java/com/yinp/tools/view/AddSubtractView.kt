package com.yinp.tools.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.yinp.tools.R
import com.yinp.tools.utils.ToolsUtils
import java.util.*

/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.tools.view
 * describe  :
 */
class AddSubtractView : LinearLayout {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    private val DIGITS = "0123456789"
    private var radius = 0//圆角
    private val subtract by lazy {
        TextView(context).apply {
            text = "−"
            setTextColor(subtractTextColor)
            setPadding(subtractLeftRightPd, topBottomPad, subtractLeftRightPd, topBottomPad)
        }
    }//减
    private val value by lazy {
        EditText(context).apply {
            textSize = valueTextSize.toFloat()
            setText(startValue.toString())
            background = null
            maxEms = maxEms
            setTextColor(valueTextColor)
            inputType = InputType.TYPE_CLASS_PHONE
            setPadding(valueLeftRightPd, topBottomPad, valueLeftRightPd, topBottomPad)
            keyListener = DigitsKeyListener.getInstance(DIGITS) //保证只能是数字
            setTypeface(null, Typeface.BOLD) //加粗
            setSelection(1)
        }
    }//值
    private val add by lazy {
        TextView(context).apply {
            textSize = subtractTextSize.toFloat()
            text = "+"
            setTextColor(addTextColor)
            setPadding(addLeftRightPd, topBottomPad, addLeftRightPd, topBottomPad)
        }
    }//加
    var rectF = RectF()
    private val linePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            color = strokeColor
            style = Paint.Style.STROKE
            strokeWidth = ToolsUtils.dpToPx(strokeWidth * 1.0f)
        }
    }
    var strokeColor = 0//边框的颜色
    var strokeWidth = 0 //边框粗细
    var addLeftRightPd = 0
    var subtractLeftRightPd: Int = 0
    var valueLeftRightPd: Int = 0
    var topBottomPad: Int = 0//边距
    var maxEms = 0 //最多8个数字
    var addTextSize = 0
    var valueTextSize: Int = 0
    var subtractTextSize: Int = 0
    var addTextColor = 0
    var valueTextColor: Int = 0
    var subtractTextColor: Int = 0
    private var startValue: Int = 0 //默认显示的数值
    var isClickAdd = false
    var isClickSubtract: Boolean = false


    private fun init(attrs: AttributeSet?) {
        isFirst = true
        context.obtainStyledAttributes(attrs, R.styleable.AddSubtractView).apply {


            radius = getInteger(R.styleable.AddSubtractView_mRadius, 0)
            strokeColor = getColor(
                R.styleable.AddSubtractView_mStrokeColor, ContextCompat.getColor(
                    context, R.color.gray_e5e5e5
                )
            )
            strokeWidth = getInteger(R.styleable.AddSubtractView_mStrokeWidth, 1) //dp
            addLeftRightPd = getInteger(R.styleable.AddSubtractView_addLeftRightPd, 4)
            subtractLeftRightPd = getInteger(R.styleable.AddSubtractView_subtractLeftRightPd, 4)
            valueLeftRightPd = getInteger(R.styleable.AddSubtractView_valueLeftRightPd, 8)
            topBottomPad = getInteger(R.styleable.AddSubtractView_topBottomPad, 4)
            maxEms = getInteger(R.styleable.AddSubtractView_maxEms, 8)
            addTextSize = getInteger(R.styleable.AddSubtractView_addTextSize, 12)
            valueTextSize = getInteger(R.styleable.AddSubtractView_valueTextSize, 14)
            subtractTextSize = getInteger(R.styleable.AddSubtractView_subtractTextSize, 12)
            addTextColor = getColor(
                R.styleable.AddSubtractView_addTextColor, ContextCompat.getColor(
                    context, R.color.gray_e5e5e5
                )
            )
            valueTextColor = getColor(
                R.styleable.AddSubtractView_valueTextColor, ContextCompat.getColor(
                    context, R.color.gray_e5e5e5
                )
            )
            subtractTextColor = getColor(
                R.styleable.AddSubtractView_subtractTextColor, ContextCompat.getColor(
                    context, R.color.gray_e5e5e5
                )
            )
            startValue = getInteger(R.styleable.AddSubtractView_defaultValue, 0)
            recycle()
        }
        if (maxEms > 8) {
            maxEms = 8
        }

        radius = ToolsUtils.dpToPx(radius)
        addLeftRightPd = ToolsUtils.dpToPx(addLeftRightPd)
        subtractLeftRightPd = ToolsUtils.dpToPx(subtractLeftRightPd)
        valueLeftRightPd = ToolsUtils.dpToPx(valueLeftRightPd)
        topBottomPad = ToolsUtils.dpToPx(topBottomPad)
        /**
         * 将视图添加进去
         */
        addView(subtract)
        addView(value)
        addView(add)
        initEvent()
        setWillNotDraw(false)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEvent() {
        subtract.setOnClickListener { v: View? ->
            val data = value.text.toString().toInt()
            if (data == 0) {
                return@setOnClickListener
            } else {
                value.setText(data.dec().toString())
            }
        }
        add.setOnClickListener { v: View? ->
            val data = value.text.toString().toInt()
            value.setText(data.inc().toString())
        }
        subtract.setOnTouchListener { v: View?, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> isClickSubtract = true
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> isClickSubtract = false
            }
            false
        }
        add.setOnTouchListener { v: View?, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> isClickAdd = true
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> isClickAdd = false
            }
            false
        }
//        subtract!!.setOnLongClickListener { v: View? ->
//            val observable: Observable<Long> =
//                Observable.interval(0, 100, TimeUnit.MILLISECONDS)
//            observable.takeUntil { aLong ->
//                if (isClickSubtract) {
//                    return@takeUntil false
//                } else {
//                    return@takeUntil true
//                }
//            }.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Observer<Long?>() {
//                    fun onError(e: Throwable?) {}
//                    fun onComplete() {}
//                    fun onSubscribe(d: Disposable?) {}
//                    fun onNext(aLong: Long?) {
//                        val data = value!!.text.toString().toInt()
//                        if (data != 0) {
//                            value.setText(--datoString())
//                        }
//                    }
//                })
//            true
//        }
//        add.setOnLongClickListener { v: View? ->
//            val observable: Observable<Long> =
//                Observable.interval(0, 100, TimeUnit.MILLISECONDS)
//            observable.takeUntil { aLong -> !isClickAdd }.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Observer<Long?>() {
//                    fun onError(e: Throwable?) {}
//                    fun onComplete() {}
//                    fun onSubscribe(d: Disposable?) {}
//                    fun onNext(aLong: Long?) {
//                        val data = value!!.text.toString().toInt()
//                        value.setText(++datoString())
//                    }
//                })
//            true
//        }
        value.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (isDefault) {
                    isDefault = false
                    return
                }
                if (TextUtils.isEmpty(s)) {
                    value.setText("0")
                    value.setSelection(1)
                }
                if (s.toString().startsWith("0") && s.length > 1) {
                    value.setText(s.toString().substring(1, s.length))
                    value.setSelection(s.length - 1)
                }
                if (s.length > maxEms) {
                    value.setText(s.toString().substring(0, s.length - 1))
                    value.setSelection(s.length - 1)
                }
                if (onValueChangedListener != null) {
                    onValueChangedListener!!.onValueChanged(value.text.toString().toInt())
                }
            }
        })
    }

    /**
     * 修改默认值
     *
     * @param startValue 一开始显示的数值
     */
    fun setStartValue(startValue: Int) {
        isDefault = true
        value.setText(startValue.toString())
    }

    private var isDefault = false

    override fun canScrollHorizontally(direction: Int): Boolean {
        return true
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return false
    }

    var isFirst = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isFirst) {
            rectF.left = 0f
            rectF.top = 0f
            rectF.right = width.toFloat()
            rectF.bottom = height.toFloat()
        }
        canvas.drawLine(
            subtract.right.toFloat(), 0f, subtract.right.toFloat(), height.toFloat(),
            linePaint
        )
        canvas.drawLine(
            add.left.toFloat(), 0f, add.left.toFloat(), height.toFloat(),
            linePaint
        )
        canvas.drawRoundRect(rectF, 20f, 20f, linePaint)
    }

    /**
     * 传递改变的数值
     */
    interface OnValueChangedListener {
        fun onValueChanged(value: Int)
    }

    private var onValueChangedListener: OnValueChangedListener? = null

    fun setOnValueChangedListener(onValueChangedListener: OnValueChangedListener?) {
        this.onValueChangedListener = onValueChangedListener
    }
}