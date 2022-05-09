package com.yinp.proappkotlin.recreation.game.fourcolormap

import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityFourColorMapBinding
import java.util.*

/**
 * author:yinp
 * 2022/5/9+20:12
 * describe:
 */
class FourColorMapActivity : BaseActivity<ActivityFourColorMapBinding>() {
    private val list: MutableList<TextView> = ArrayList()

    override fun initViews() {
        initClick(
            bd.header.headerBackImg,
            bd.tvBlue,
            bd.tvGreen,
            bd.tvRed,
            bd.tvWhite,
            bd.tvYellow,
            bd.stvRestart
        )
        list.add(bd.tvRed)
        list.add(bd.tvYellow)
        list.add(bd.tvBlue)
        list.add(bd.tvGreen)
        list.add(bd.tvWhite)
    }

    var isSelected = booleanArrayOf(false, false, false, false, false)

    override fun onClick(v: View) {
        super.onClick(v)
        if (v === bd.header.headerBackImg) {
            finish()
        } else if (v === bd.tvRed) {
            if (!isSelected[0]) {
                closeOpenAnimation()
                selectAnimation(list[0])
                isSelected[0] = true
                bd.mvMain.setColor(Color.RED)
            }
        } else if (v === bd.tvYellow) {
            if (!isSelected[1]) {
                closeOpenAnimation()
                selectAnimation(list[1])
                isSelected[1] = true
                bd.mvMain.setColor(Color.YELLOW)
            }
        } else if (v === bd.tvBlue) {
            if (!isSelected[2]) {
                closeOpenAnimation()
                selectAnimation(list[2])
                isSelected[2] = true
                bd.mvMain.setColor(Color.BLUE)
            }
        } else if (v === bd.tvGreen) {
            if (!isSelected[3]) {
                closeOpenAnimation()
                selectAnimation(list[3])
                isSelected[3] = true
                bd.mvMain.setColor(Color.GREEN)
            }
        } else if (v === bd.tvWhite) {
            if (!isSelected[4]) {
                closeOpenAnimation()
                selectAnimation(list[4])
                isSelected[4] = true
                bd.mvMain.setColor(Color.WHITE)
            }
        } else if (v === bd.stvRestart) {
            bd.mvMain.restart()
        }
    }


    //关闭已经打开的动画
    fun closeOpenAnimation() {
        for (i in isSelected.indices) {
            if (isSelected[i]) {
                cancelAnimation(list[i])
                isSelected[i] = false
            }
        }
    }

    //选中以后的动画效果，放大
    fun selectAnimation(tv: TextView) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.bg_scale_amplification)
        tv.startAnimation(anim)
    }

    //将放大的效果复原
    fun cancelAnimation(tv: TextView) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.bg_scale_shrink)
        tv.startAnimation(anim)
    }

    override fun getBinding() = ActivityFourColorMapBinding.inflate(layoutInflater)
}