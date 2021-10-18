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
class GluttonousSnakeView: View {
    constructor(context: Context?, attrs: AttributeSet?):super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int):super(context, attrs, defStyleAttr) {
        init()
    }

    private val aLinkList by lazy {
        ALinkList()
    }

    /**
     * 蛇
     */
    private var snakePaint: Paint? = null
    private var snakePaint2: Paint? = null
    private var snakeWidth = 0

    /**
     * 食物
     */
    private var foodPaint: Paint? = null
    private var foodPoint: Point? = null

    /**
     * 边框
     */
    private var borderPaint: Paint? = null
    private var borderRect: Rect? = null

    private val part = 50 //能被100整除


    private fun init() {
        snakePaint = Paint()
        snakePaint2 = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        foodPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        borderPaint = Paint()
        borderPaint!!.strokeWidth = padding.toFloat()
        borderPaint!!.color = Color.RED
        borderPaint!!.style = Paint.Style.STROKE
    }

    private var mWidth= 0 //当前view的宽度

    private var mHeight= 0 //当前view的高度

    var isFirst = true
    private var totalLRWidth = 0 //左右间距之和

    private val padding = 4 //边框

    private var duration = 60 //蛇的移动速度


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val screenWidth = AppUtils.getWidthPixels(context) as Int
        val screenHeight = AppUtils.getHeightPixels(context) as Int
        totalLRWidth = 100 + screenWidth % 100 - padding * 2
        mHeight = screenWidth - totalLRWidth - padding * 2
        mWidth = mHeight
        borderRect = Rect(padding, padding, mWidth - padding, mHeight - padding)
        if (isFirst) {
            snakeWidth = mWidth / part
            snakePaint!!.style = Paint.Style.STROKE
            snakePaint!!.strokeWidth = snakeWidth.toFloat()
            snakePaint!!.color = Color.RED
            snakePaint2!!.strokeWidth = snakeWidth.toFloat()
            snakePaint2!!.color = Color.TRANSPARENT
            foodPaint!!.color = Color.BLUE
            foodPaint!!.strokeWidth = snakeWidth.toFloat()
            initSnakeAndFood()
            isFirst = false
        }
        setMeasuredDimension(mWidth + padding * 2, mHeight + padding * 2)
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
            duration = 100
            isRun = false
            isEnd = true
            timer = null
            timerTask = null
        } else {
            if (foodPoint!!.x == aLinkList.first!!.x && foodPoint!!.y == aLinkList.first!!.y) {
                foodPoint = null
                duration -= 4
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
            timer = Timer()
            timer!!.schedule(timerTask, 0, duration.toLong())
        }
    }

    fun reStart() {
        if (timer != null) {
            timer!!.cancel()
        }
        duration = 100
        isRun = false
        isEnd = true
        timer = null
        timerTask = null
        startMove()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPoints(aLinkList.getAll()!!, snakePaint!!)
        canvas.drawPoint(foodPoint!!.x.toFloat(), foodPoint!!.y.toFloat(), foodPaint!!)
        canvas.drawRect(borderRect!!, borderPaint!!)
    }

    /**
     * 食物的生成
     */
    private fun randomFood() {
        val random = Random()
        var x = random.nextInt(part)
        var y = random.nextInt(part)
        if (x == 0) {
            x = 1
        }
        if (y == 0) {
            y = 1
        }
        val `is`: Boolean = aLinkList.contains(x * snakeWidth, y * snakeWidth)
        if (!`is`) {
            foodPoint = Point(x * snakeWidth, y * snakeWidth)
        } else {
            randomFood()
        }
    }

    /**
     * 初始化蛇的位置和食物的位置，表示重开
     */
    private fun initSnakeAndFood() {
        aLinkList.clearAll()
        aLinkList.insertFirst(mWidth / 2, mWidth / 2)
        aLinkList.insertFirst(mWidth / 2 + snakeWidth, mWidth / 2)
        aLinkList.insertFirst(mWidth / 2 + snakeWidth * 2, mWidth / 2)
        aLinkList.insertFirst(mWidth / 2 + snakeWidth * 3, mWidth / 2)
        randomFood()
    }

    /**
     * 停止移动，可以再次开始
     */
    fun stopMove() {
        isRun = false
        timer!!.cancel()
        timer = null
    }

    /**
     * 结束时
     */
    fun remove() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        if (timerTask != null) {
            timerTask!!.cancel()
            timerTask = null
        }
    }
}