package com.yinp.tools.view

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * @author   :yinpeng
 * date      :2021/10/14
 * package   :com.yinp.tools.view
 * describe  :
 */
class ColorFlipPagerTitleView(context: Context) : SimplePagerTitleView(context) {
    private var mChangePercent = 0.5f

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor)
        } else {
            setTextColor(mSelectedColor)
        }
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor)
        } else {
            setTextColor(mNormalColor)
        }
    }

    override fun onSelected(index: Int, totalCount: Int) {}

    override fun onDeselected(index: Int, totalCount: Int) {}

    fun getChangePercent(): Float {
        return mChangePercent
    }

    fun setChangePercent(changePercent: Float) {
        mChangePercent = changePercent
    }
}