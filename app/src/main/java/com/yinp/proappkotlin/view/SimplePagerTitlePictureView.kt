package com.yinp.proappkotlin.view

import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.view
 * describe  :
 */
class SimplePagerTitlePictureView(val mContext: Context) : AppCompatTextView(mContext, null),
    IMeasurablePagerTitleView {
    init {
        initSomething()
    }
    private var mSelectedColor = 0
    private var mNormalColor = 0
    private val mChangePercent = 0.5f

    private fun setmSelectedDrawable(mSelectedDrawable: Int) {
        this.mSelectedDrawable = mSelectedDrawable
    }

    private fun setmNormalDrawable(mNormalDrawable: Int) {
        this.mNormalDrawable = mNormalDrawable
    }

    protected var mSelectedDrawable = -1
    protected var mNormalDrawable = -1

    private fun initSomething() {
        gravity = Gravity.CENTER
        val padding = UIUtil.dip2px(mContext, 10.0)
        setPadding(padding, 0, padding, 0)
        setSingleLine()
        ellipsize = TextUtils.TruncateAt.END
    }

    override fun onSelected(index: Int, totalCount: Int) {}

    override fun onDeselected(index: Int, totalCount: Int) {}

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor)
            if (mNormalDrawable != -1) {
                setCompoundDrawablesWithIntrinsicBounds(
                    null, ContextCompat.getDrawable(
                        mContext, mNormalDrawable
                    ), null, null
                )
            }
        } else {
            setTextColor(mSelectedColor)
            if (mSelectedDrawable != -1) {
                setCompoundDrawablesWithIntrinsicBounds(
                    null, ContextCompat.getDrawable(
                        mContext, mSelectedDrawable
                    ), null, null
                )
            }
        }
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor)
            if (mSelectedDrawable != -1) {
                setCompoundDrawablesWithIntrinsicBounds(
                    null, ContextCompat.getDrawable(
                        mContext, mSelectedDrawable
                    ), null, null
                )
            }
        } else {
            setTextColor(mNormalColor)
            if (mNormalDrawable != -1) {
                setCompoundDrawablesWithIntrinsicBounds(
                    null, ContextCompat.getDrawable(
                        mContext, mNormalDrawable
                    ), null, null
                )
            }
        }
    }

    override fun getContentLeft(): Int {
        val bound = Rect()
        var longestString = ""
        if (text.toString().contains("\n")) {
            val brokenStrings = text.toString().split("\\n").toTypedArray()
            for (each in brokenStrings) {
                if (each.length > longestString.length) longestString = each
            }
        } else {
            longestString = text.toString()
        }
        paint.getTextBounds(longestString, 0, longestString.length, bound)
        val contentWidth = bound.width()
        return left + width / 2 - contentWidth / 2
    }

    override fun getContentTop(): Int {
        val metrics = paint.fontMetrics
        val contentHeight = metrics.bottom - metrics.top
        return (height / 2 - contentHeight / 2).toInt()
    }

    override fun getContentRight(): Int {
        val bound = Rect()
        var longestString = ""
        if (text.toString().contains("\n")) {
            val brokenStrings = text.toString().split("\\n").toTypedArray()
            for (each in brokenStrings) {
                if (each.length > longestString.length) longestString = each
            }
        } else {
            longestString = text.toString()
        }
        paint.getTextBounds(longestString, 0, longestString.length, bound)
        val contentWidth = bound.width()
        return left + width / 2 + contentWidth / 2
    }

    override fun getContentBottom(): Int {
        val metrics = paint.fontMetrics
        val contentHeight = metrics.bottom - metrics.top
        return (height / 2 + contentHeight / 2).toInt()
    }

    fun getSelectedColor(): Int {
        return mSelectedColor
    }

    fun setSelectedColor(selectedColor: Int) {
        mSelectedColor = selectedColor
    }

    fun getNormalColor(): Int {
        return mNormalColor
    }

    fun setNormalColor(normalColor: Int) {
        mNormalColor = normalColor
    }
}