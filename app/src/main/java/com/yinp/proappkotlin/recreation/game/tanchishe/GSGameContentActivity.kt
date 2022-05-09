package com.yinp.proappkotlin.recreation.game.tanchishe

import android.view.View
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityGSGameContentBinding

/**
 * author:yinp
 * 2022/5/7+21:24
 * describe:
 */
class GSGameContentActivity: BaseActivity<ActivityGSGameContentBinding>() {
    override fun initViews() {
        initClick(
            bd.header.headerBackImg,
            bd.stvUp,
            bd.stvLeft,
            bd.stvDown,
            bd.stvRight,
            bd.stvStop,
            bd.stvStart,
            bd.stvRestart
        )
    }
    override fun onClick(v: View) {
        super.onClick(v)
        when {
            v === bd.header.headerBackImg -> {
                finish()
            }
            v === bd.stvUp -> {
                bd.gsView.setDirection(1)
            }
            v === bd.stvDown -> {
                bd.gsView.setDirection(3)
            }
            v === bd.stvLeft -> {
                bd.gsView.setDirection(0)
            }
            v === bd.stvRight -> {
                bd.gsView.setDirection(2)
            }
            v === bd.stvStop -> {
                bd.gsView.stopMove()
            }
            v === bd.stvStart -> {
                bd.gsView.startMove()
            }
            v === bd.stvRestart -> {
                bd.gsView.reStart()
            }
        }
    }
    override fun getBinding() = ActivityGSGameContentBinding.inflate(layoutInflater)
}