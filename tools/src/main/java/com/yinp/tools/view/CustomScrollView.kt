package com.yinp.tools.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.yinp.tools.R
import com.yinp.tools.dpToPx
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.pow


/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.tools.view
 * describe  :
 */
class CustomScrollView : View {
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
        }
    }
    private var mLightColor = 0
    private var mDarkColor: Int = 0
    private var mHalfWidth = 0f
    private var mHalfHeight: Float = 0f
    private var mQuarterHeight: Float = 0f
    private var maxTextSize = 0f
    private var minTextSize: Float = 0f
    private var mTextSizeRange: Float = 0f
    private var mTextSpacing = 0f
    private var mHalfTextSpacing: Float = 0f

    private var mScrollDistance = 0f
    private var mLastTouchY = 0f
    private var mDataList: List<String> = ArrayList()
    private var mSelectedIndex = 0
    private var mCanScroll = true //是否能够滚动

    private var mOnSelectListener: OnSelectListener? = null

    private var mTimer: Timer = Timer()
    private var mTimerTask: TimerTask? = null
    private val mHandler by lazy {
        ScrollHandler(this)
    }

    /**
     * 自动回滚到中间的速度
     */
    private val AUTO_SCROLL_SPEED = 10f

    /**
     * 透明度：最小 120，最大 255，极差 135
     */
    private val TEXT_ALPHA_MIN = 120
    private val TEXT_ALPHA_RANGE = 135

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initPaint(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initPaint(attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initPaint(attrs)
    }

    /**
     * 选择结果回调接口
     */
    interface OnSelectListener {
        fun onSelect(view: View?, selected: String?, position: Int)
    }

    private class ScrollTimerTask(handler: Handler) : TimerTask() {
        private val mWeakHandler: WeakReference<Handler> = WeakReference(handler)
        override fun run() {
            val handler: Handler = mWeakHandler.get() ?: return
            handler.sendEmptyMessage(0)
        }

    }

    private class ScrollHandler(view: CustomScrollView) : Handler() {
        private val mWeakView: WeakReference<CustomScrollView> = WeakReference(view)
        override fun handleMessage(msg: Message) {
            val view: CustomScrollView = mWeakView.get() ?: return
            view.keepScrolling()
        }

    }

    private var selectedColor: Drawable? = null
    private var unSelectedColor: Drawable? = null

    private fun initPaint(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.CustomScrollView).apply {
            maxTextSize = getInteger(R.styleable.CustomScrollView_textMaxSize, 18).toFloat()
            minTextSize = getInteger(R.styleable.CustomScrollView_textMinSize, 14).toFloat()
            selectedColor = getDrawable(R.styleable.CustomScrollView_selectedColor)
            unSelectedColor = getDrawable(R.styleable.CustomScrollView_unSelectedColor)
            recycle()
        }

        minTextSize = minTextSize.dpToPx()
        maxTextSize = maxTextSize.dpToPx()

        if (selectedColor is ColorDrawable && unSelectedColor is ColorDrawable) {
            mLightColor = (selectedColor as ColorDrawable).color //被选中的颜色
            mDarkColor = (unSelectedColor as ColorDrawable).color
        } else {
            mLightColor = ContextCompat.getColor(context, R.color.blue) //被选中的颜色
            mDarkColor = ContextCompat.getColor(context, R.color.black99)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHalfWidth = measuredWidth / 2f
        val height = measuredHeight
        mHalfHeight = height / 2f
        mQuarterHeight = height / 4f
        mTextSizeRange = maxTextSize - minTextSize //字体大小变化
        mTextSpacing = minTextSize * 2.8f
        mHalfTextSpacing = mTextSpacing / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mSelectedIndex >= mDataList.size) return

        // 绘制选中的 text
        drawText(canvas, mLightColor, mScrollDistance, mDataList[mSelectedIndex])

        // 绘制选中上方的 text
        for (i in 1..mSelectedIndex) {
            drawText(
                canvas, mDarkColor, mScrollDistance - i * mTextSpacing,
                mDataList[mSelectedIndex - i]
            )
        }

        // 绘制选中下方的 text
        val size = mDataList.size - mSelectedIndex
        for (i in 1 until size) {
            drawText(
                canvas, mDarkColor, mScrollDistance + i * mTextSpacing,
                mDataList[mSelectedIndex + i]
            )
        }
    }

    private fun drawText(canvas: Canvas, textColor: Int, offsetY: Float, text: String) {
        if (TextUtils.isEmpty(text)) return
        var scale = 1 - (offsetY / mQuarterHeight).toDouble().pow(2.0).toFloat()
        scale = if (scale < 0) 0F else scale
        mPaint.textSize = minTextSize + mTextSizeRange * scale
        mPaint.color = textColor
        mPaint.alpha = TEXT_ALPHA_MIN + (TEXT_ALPHA_RANGE * scale).toInt()

        // text 居中绘制，mHalfHeight + offsetY 是 text 的中心坐标
        val fm: Paint.FontMetrics = mPaint.fontMetrics
        val baseline: Float = mHalfHeight + offsetY - (fm.top + fm.bottom) / 2f
        canvas.drawText(text, mHalfWidth, baseline, mPaint)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return mCanScroll && super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                cancelTimerTask()
                mLastTouchY = event.y //当期触点的Y坐标
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetY = event.y //滑动后当期触摸点
                mScrollDistance += offsetY - mLastTouchY //意味着滑动最大距离是最开始触点的y坐标值
                if (mScrollDistance > mHalfTextSpacing) {
                    if (mSelectedIndex == 0) {
                        mLastTouchY = offsetY
                        invalidate()
                        return true
                    } else {
                        mSelectedIndex--
                    }
                    mScrollDistance -= mTextSpacing
                } else if (mScrollDistance < -mHalfTextSpacing) {
                    if (mSelectedIndex == mDataList.size - 1) {
                        mLastTouchY = offsetY
                        invalidate()
                        return true
                    } else {
                        mSelectedIndex++
                    }
                    mScrollDistance += mTextSpacing
                }
                mLastTouchY = offsetY
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                // 抬起手后 mSelectedIndex 由当前位置滚动到中间选中位置
                if (abs(mScrollDistance) < 0.01) {
                    mScrollDistance = 0f
                    return true
                }
                cancelTimerTask()
                mTimerTask = ScrollTimerTask(mHandler)
                mTimer.schedule(mTimerTask, 0, 10)
            }
        }
        return true
    }

    private fun cancelTimerTask() {
        with(mTimerTask) {
            this?.cancel()
            null
        }
        mTimer.let {
            mTimer.purge()
        }
    }

    private fun keepScrolling() {
        if (abs(mScrollDistance) < AUTO_SCROLL_SPEED) {
            mScrollDistance = 0f
            if (mTimerTask != null) {
                cancelTimerTask()
                if (mOnSelectListener != null && mSelectedIndex < mDataList.size) {
                    mOnSelectListener!!.onSelect(this, mDataList[mSelectedIndex], mSelectedIndex)
                }
            }
        } else if (mScrollDistance > 0) {
            // 向下滚动
            mScrollDistance -= AUTO_SCROLL_SPEED
        } else {
            // 向上滚动
            mScrollDistance += AUTO_SCROLL_SPEED
        }
        invalidate()
    }

    /**
     * 设置数据
     */
    fun setDataList(list: List<String>?) {
        if (list == null || list.isEmpty()) return
        mDataList = list
        // 重置 mSelectedIndex，防止溢出
        mSelectedIndex = 0
        invalidate()
    }

    /**
     * 选择选中项
     */
    fun setSelected(index: Int) {
        if (index >= mDataList.size) return
        mSelectedIndex = index
        invalidate()
    }

    fun getSelected(): Int {
        return mSelectedIndex
    }

    fun getDataList(): List<String> {
        return mDataList
    }

    /**
     * 设置是否可以滚动
     */
    fun setmCanScroll(canScroll: Boolean) {
        mCanScroll = canScroll
    }

    /**
     * 设置选择结果监听
     */
    fun setOnSelectListener(listener: OnSelectListener?) {
        mOnSelectListener = listener
    }

    /**
     * 销毁资源
     */
    fun onDestroy() {
        mOnSelectListener = null
        mHandler.removeCallbacksAndMessages(null)
        cancelTimerTask()
        with(mTimer) {
            cancel()
            null
        }
    }
}