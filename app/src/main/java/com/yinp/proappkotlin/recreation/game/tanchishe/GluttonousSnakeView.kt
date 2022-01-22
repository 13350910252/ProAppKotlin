package com.yinp.proappkotlin.recreation.game.tanchishe

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.yinp.proappkotlin.utils.AppUtils
import java.util.*

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.recreation.game
 * describe  :
 */
class GluttonousSnakeView : View {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private val aLinkList by lazy {
        ALinkList()
    }

    /**
     * 蛇
     */
    private val snakePaint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = snakeWidth.toFloat()
            color = Color.RED
        }
    }
    private val snakePaint2 by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            strokeWidth = snakeWidth.toFloat()
            color = Color.TRANSPARENT
        }
    }
    private var snakeWidth = 0

    /**
     * 食物
     */
    private val foodPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            color = Color.BLUE
            strokeWidth = snakeWidth.toFloat()
        }
    }
    private var foodPoint: Point? = null

    /**
     * 边框
     */
    private val borderPaint by lazy {
        Paint().apply {
            strokeWidth = mPadding.toFloat()
            color = Color.RED
            style = Paint.Style.STROKE
        }
    }
    private lateinit var borderRect: Rect

    private val mPart = 50 //能被100整除


    private fun init() {
    }

    private var mWidth = 0 //当前view的宽度

    private var mHeight = 0 //当前view的高度

    private var mFirst = true
    private var mTotalLRWidth = 0 //左右间距之和

    private val mPadding = 4 //边框

    private var mDuration = 60 //蛇的移动速度


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val screenWidth = AppUtils.getWidthPixels()
        val screenHeight = AppUtils.getHeightPixels()
        mTotalLRWidth = 100 + screenWidth % 100 - mPadding * 2
        mHeight = screenWidth - mTotalLRWidth - mPadding * 2
        mWidth = mHeight
        borderRect = Rect(mPadding, mPadding, mWidth - mPadding, mHeight - mPadding)
        if (mFirst) {
            snakeWidth = mWidth / mPart
            initSnakeAndFood()
            mFirst = false
        }
        setMeasuredDimension(mWidth + mPadding * 2, mHeight + mPadding * 2)
    }

    /**
     * 设置蛇前进的方向
     */
    private var direction = 2

    fun setDirection(gravity: Int) {
        if (direction + gravity == 2 || direction + gravity == 4) {
            return
        }
        direction = gravity
    }

    /**
     * 移动逻辑
     */
    private fun start() {
        when (direction) {
            0 -> aLinkList.insertFirst(aLinkList.first!!.x - snakeWidth, aLinkList.first!!.y)
            1 -> aLinkList.insertFirst(aLinkList.first!!.x, aLinkList.first!!.y - snakeWidth)
            2 -> aLinkList.insertFirst(aLinkList.first!!.x + snakeWidth, aLinkList.first!!.y)
            3 -> aLinkList.insertFirst(aLinkList.first!!.x, aLinkList.first!!.y + snakeWidth)
        }
        if (aLinkList.isFirstRepetition() || aLinkList.first!!.x >= mWidth || aLinkList.first!!.x <= 0 || aLinkList.first!!.y >= mHeight || aLinkList.first!!.y <= 0) {
            timer!!.cancel()
            mDuration = 100
            isRun = false
            isEnd = true
            timer = null
            timerTask = null
        } else {
            if (foodPoint!!.x == aLinkList.first!!.x && foodPoint!!.y == aLinkList.first!!.y) {
                foodPoint = null
                mDuration -= 4
            } else {
                aLinkList.removeLast()
            }
            if (foodPoint == null) {
                randomFood()
            }
            postInvalidate()
        }
    }

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var isRun = false
    private var isEnd = false

    /**
     * 开始移动
     */
    fun startMove() {
        if (isEnd) {
            initSnakeAndFood()
            isEnd = false
            postInvalidate()
        }
        if (isRun) {
            return
        }
        isRun = true
        if (timerTask == null) {
            timerTask = object : TimerTask() {
                override fun run() {
                    start()
                }
            }
        }
        if (timer == null) {
            timer = Timer().apply {
                schedule(timerTask, 0, mDuration.toLong())
            }
        }
    }

    fun reStart() {
        timer = timer?.run {
            cancel()
            null
        }
        mDuration = 100
        isRun = false
        isEnd = true
        timerTask = null
        startMove()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPoints(aLinkList.getAll()!!, snakePaint)
        canvas.drawPoint(foodPoint!!.x.toFloat(), foodPoint!!.y.toFloat(), foodPaint)
        canvas.drawRect(borderRect, borderPaint)
    }

    /**
     * 食物的生成
     */
    private fun randomFood() {
        val random = Random()
        var x = random.nextInt(mPart)
        var y = random.nextInt(mPart)
        if (x == 0) {
            x = 1
        }
        if (y == 0) {
            y = 1
        }
        val hasFood: Boolean = aLinkList.contains(x * snakeWidth, y * snakeWidth)
        if (!hasFood) {
            foodPoint = Point(x * snakeWidth, y * snakeWidth)
        } else {
            randomFood()
        }
    }

    /**
     * 初始化蛇的位置和食物的位置，表示重开
     */
    private fun initSnakeAndFood() {
        aLinkList.run {
            clearAll()
            insertFirst(mWidth / 2, mWidth / 2)
            insertFirst(mWidth / 2 + snakeWidth, mWidth / 2)
            insertFirst(mWidth / 2 + snakeWidth * 2, mWidth / 2)
            insertFirst(mWidth / 2 + snakeWidth * 3, mWidth / 2)
        }
        randomFood()
    }

    /**
     * 停止移动，可以再次开始
     */
    fun stopMove() {
        isRun = false
        timer = timer?.run {
            cancel()
            null
        }
    }

    /**
     * 结束时
     */
    fun remove() {
        timer = timer?.run {
            cancel()
            null
        }
        timerTask = timerTask?.run {
            cancel()
            null
        }
    }
}