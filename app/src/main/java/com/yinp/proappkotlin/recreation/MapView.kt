package com.yinp.proappkotlin.recreation

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.graphics.PathParser
import com.yinp.proappkotlin.R
import org.w3c.dom.Element
import java.util.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.view
 * describe  :
 */
class MapView : View {
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private var mItemList = mutableListOf<ProvinceItem>() //省
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }
    private var totalRect: RectF? = null
    private var mScale = 1.0f //中国地图的缩放比例

    private var isEnd = false

    private fun init() {
        mThread.start()
    }

    //确定是需要缩小还是放大，scale存放系数
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //获取当前控件的高度 让地图宽高适配当前控件
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (totalRect != null) {
            //当所有的地图Path加载出来以后，requestLayout()会调用方法，就有值了。
            val mapWidth = totalRect!!.width().toDouble()
            mScale = (width / mapWidth).toFloat() //获取控件高度为了让地图能缩放到和控件宽高适配
        }
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }

    /**
     * 加载地图的路径相对比较耗时，这里开启子线程来加载
     */
    private val mThread: Thread = object : Thread() {
        override fun run() {
            super.run()
            val inputStream = Resources.getSystem().openRawResource(R.raw.chinasvg)
            val factory = DocumentBuilderFactory.newInstance() //获取DocumentBuilderFactory实例
            var builder: DocumentBuilder?
            try {
                builder = factory.newDocumentBuilder()
                val doc = builder.parse(inputStream) //解析svg的输入流
                val rootElement = doc.documentElement //该属性允许直接访问文档的文档元素的子节点
                val items = rootElement.getElementsByTagName("path") //获取地图的整个上下左右位置，
                var left = -1f
                var right = -1f
                var top = -1f
                var bottom = -1f
                val list: MutableList<ProvinceItem> = ArrayList<ProvinceItem>() //存放解析出来的省
                for (i in 0 until items.length) {
                    val element = items.item(i) as Element
                    val pathData = element.getAttribute("android:pathData")
                    val path = PathParser.createPathFromPathData(pathData) //获取每一个path，即要绘画的路径
                    val provinceItem = ProvinceItem(path) //设置路径,初始化省对象
                    // 取每个省的上下左右 最后拿出最小或者最大的来充当 总地图的上下左右
                    val rect = RectF()
                    provinceItem.provinceRectF = rect
                    path.computeBounds(rect, false) //获取path的边界值,用于比对和判断选择
                    //获取最小的左和上，最大的右和下确定整个中国地图的矩形,确定地图最大范围
                    left = if (left == -1f) rect.left else Math.min(left, rect.left)
                    right = if (right == -1f) rect.right else Math.max(right, rect.right)
                    top = if (top == -1f) rect.top else Math.min(top, rect.top)
                    bottom = if (bottom == -1f) rect.bottom else Math.max(bottom, rect.bottom)
                    list.add(provinceItem)
                }
                mItemList.addAll(list)
                totalRect = RectF(left, top, right, bottom) //设置地图的上下左右位置
                // 加载完以后刷新界面,在主线程中就不用Looper.getMainLooper();
                Handler(Looper.getMainLooper()).post { //requestLayout方法会导致View的onMeasure、onLayout、onDraw方法被调用；
                    // invalidate方法则只会导致View的onDraw方法被调用
                    requestLayout()
                    invalidate()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 相邻块儿的颜色不能相同
     *
     * @param provinceItem
     * @return false 即是白色或者相邻色不同
     */
    fun neighborColor(provinceItem: ProvinceItem): Boolean {
        if (mColor == Color.WHITE) {
            return false
        }
        for (i in mItemList.indices) {
            if (RectF.intersects(provinceItem.provinceRectF, mItemList[i].provinceRectF)) {
                if (provinceItem === mItemList[i]) {
                    continue
                }
                if (mItemList[i].selectColor == Color.WHITE) {
                    continue
                }
                if (mItemList[i].selectColor == mColor) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 手指触摸的时候触发,因为大小按比例变化，所以点击的点的具体位置也要按比例变化。
     *
     * @param event
     * @return
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (!isEnd) {
                handleTouch(event.x / mScale, event.y / mScale)
            } else {
                Toast.makeText(context, "恭喜你，四色地图已经完成", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 获取选中的块儿，并判断是否颜色相同
     *
     * @param x
     * @param y
     */
    private fun handleTouch(x: Float, y: Float) {
        val selectItem: ProvinceItem? = null
        for (i in mItemList.indices) {
            if (mItemList[i].isContains(x, y)) {
                Log.d("abcd", "handleTouch: $i")
                if (!FCM_judge.judge(mItemList, i, mColor)) {
                    mItemList[i].selectColor = mColor
                    postInvalidate()
                } else {
                    Toast.makeText(context, "相邻省颜色不能相同", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //        for (ProvinceItem provinceItem : itemList) {
//            if (provinceItem.isContains(x, y)) {
//                selectItem = provinceItem;//选中的省
//                //返回true就不能为地图块儿上色
//                if (!neighborColor(selectItem)) {
//                    provinceItem.selectColor = mColor;
//                } else {
//                    Toast.makeText(mContext, "相邻省颜色不能相同", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
        if (selectItem != null) {
            //刷新调用onDraw
//            postInvalidate();
        }
    }

    /**
     * 判断地图是否填满
     *
     * @return
     */
    fun isFull(): Boolean {
        for (i in mItemList.indices) {
            if (i == 11 || i == 19) {
                continue
            } else {
                if (mItemList[i].selectColor == Color.WHITE) {
                    return false
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mItemList.isNotEmpty()) {
            canvas.scale(mScale, mScale) //把画布缩放匹配到本控件的宽高控件有多大，地图就放大或缩小到适应
            for (provinceItem in mItemList) {
                provinceItem.drawItem(canvas, mPaint)
            }
            if (isFull()) {
                //在这里添加游戏结束的处理
                isEnd = true
                Toast.makeText(context, "恭喜你，四色地图已经完成", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var mColor = Color.RED

    //改变画笔的颜色
    fun setColor(color: Int) {
        mColor = color
    }

    fun restart() {
        isEnd = false
        for (i in mItemList.indices) {
            mItemList[i].selectColor = Color.WHITE
        }
        postInvalidate()
    }
}