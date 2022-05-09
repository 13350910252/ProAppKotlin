package com.yinp.proappkotlin.recreation.game.fourcolormap

import android.graphics.*

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.recreation
 * describe  :
 */
class ProvinceItem {
    var selectColor = Color.WHITE //地图快儿的颜色

    private var path: Path
    var provinceRectF = RectF()

    constructor(path: Path) {
        this.path = path
    }

    fun drawItem(canvas: Canvas, paint: Paint) {
        paint.run {
            color = selectColor
            style = Paint.Style.FILL
            canvas.drawPath(path, this)
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = -0x2f170c
            canvas.drawPath(path, this)
        }
    }

    //判断点击屏幕的点在地图的哪一块儿
    fun isContains(x: Float, y: Float): Boolean {
        //注意注意这块是来判断点击位置的 主要知识点Region
        val rectF = RectF()
        path.computeBounds(rectF, true) //获取path的边界
        val region = Region()
        region.setPath(
            path, Region(
                rectF.left.toInt(),
                rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt()
            )
        )
        return region.contains(x.toInt(), y.toInt())
    }
}