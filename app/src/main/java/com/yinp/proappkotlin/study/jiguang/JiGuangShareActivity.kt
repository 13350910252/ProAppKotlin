package com.yinp.proappkotlin.study.jiguang

import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityJiGuangShareBinding
import com.yinp.proappkotlin.utils.StatusBarUtil

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.study.jiguang
 * describe  :
 */
class JiGuangShareActivity : BaseActivity<ActivityJiGuangShareBinding>() {
    override fun initViews() {
        bd.header.headerCenterTitle.text = "ζεεδΊ«"
        initClick(bd.header.headerBackImg)
    }

    override fun getBinding() = ActivityJiGuangShareBinding.inflate(layoutInflater)
}