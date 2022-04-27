package com.yinp.tools.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.tools.view
 * describe  :
 */
class CustomDialogView : FrameLayout {
    /**
     * 背景页，主要是实现阴影效果,随便一个viewgroup都可以
     */
    private lateinit var dialogBgView: View

    /**
     * 弹窗页，显示弹窗，其内可以拜访需要显示的页面
     * 也是自定义的，需要对点击事件进行拦截。
     */
    private lateinit var dialogContentView: View

    /**
     * 背景view的宽高
     */
    private var bgWidth = 0
    private var bgHeight = 0

    private var contentWidth = 0
    private var contentHeight = 0

    /**
     * 是否时第一次初始化
     */
    private var isFirstLoad = false

    /**
     * 是否能够结束动画
     */
    private var isCanEnd = false

    /**
     * 判断弹窗是否打开了
     */
    private var isStarting = false

    /**
     * 动画的时长
     */
    private var duration: Long = 400

    /**
     * 存储弹窗页面的矩形，用于点击事件计算，
     * 方便控制弹窗收回
     */
    private var rectContent: Rect? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        initFirstView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
        0
    ) {
        initFirstView()
    }

    private fun initFirstView() {
        isFirstLoad = true
    }

    /**
     * 获取当前viewGroup中的所有子view
     * 对第一个子view重新摆放位置，放在上边，相当于隐藏，需要时再弹出
     * 所以必须固定格式
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (childCount != 2) {
            return
        }
        if (isFirstLoad) {
            dialogBgView = getChildAt(0)
            dialogContentView = getChildAt(1)
            bgWidth = dialogBgView.width
            bgHeight = dialogBgView.height
            contentWidth = dialogContentView.width
            contentHeight = dialogContentView.height
            isFirstLoad = false
            rectContent = Rect(
                dialogContentView.left,
                dialogContentView.top,
                dialogContentView.right,
                dialogContentView.bottom
            )
        }
        dialogContentView.layout(0, -contentHeight, contentWidth, 0)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (!rectContent!!.contains(
                    event.x.toInt(),
                    event.y.toInt()
                )
            ) {
                endAnimation()
            }
        }
        //必须对点击事件进行拦截
        return true
    }

    /**
     * 弹窗打开和关闭时的动画
     */
    fun startAnimations() {
        if (isStarting) {
            return
        }
        //清理动画
        dialogContentView.clearAnimation()
        dialogBgView.clearAnimation()
        isCanEnd = false
        isStarting = true
        val alphaAnimator = ObjectAnimator.ofFloat(dialogBgView, "alpha", 0f, 0.6f)
        val translationAnimator =
            ObjectAnimator.ofFloat(dialogContentView, "translationY", 0f, contentHeight * 1.0f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, translationAnimator)
        animatorSet.duration = duration
        animatorSet.start()
        if (animatorSet.isStarted) {
            isCanEnd = true
        }
    }

    /**
     * 结束动画
     */
    fun endAnimation() {
        //目的时判断当前是否有开启动画，有才允许关闭动画
        if (!isCanEnd) {
            return
        }
        //清理动画
        dialogContentView.clearAnimation()
        dialogBgView.clearAnimation()
        isCanEnd = false
        isStarting = false
        val alphaOb = ObjectAnimator.ofFloat(dialogBgView, "alpha", 0.6f, 0f)
        val translationYOb =
            ObjectAnimator.ofFloat(dialogContentView, "translationY", 1.0f * contentHeight, 0f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaOb, translationYOb)
        animatorSet.duration = duration
        animatorSet.start()
    }

    /**
     * 监听弹窗的打开和关闭
     */
    private var onDialogOpenListener: OnDialogOpenListener? = null

    interface OnDialogOpenListener {
        fun onDialogOpen(isOpen: Boolean)
    }

    fun setOnDialogOpenListener(onDialogOpenListener: OnDialogOpenListener?) {
        this.onDialogOpenListener = onDialogOpenListener
    }

    /**
     * 设置动画的时长
     *
     * @param duration
     */
    fun setDuration(duration: Long) {
        this.duration = duration
    }

    fun isStarting(): Boolean {
        return isStarting
    }
}