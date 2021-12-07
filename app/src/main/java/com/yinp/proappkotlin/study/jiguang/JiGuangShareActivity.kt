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
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(this))
        bd.header.headerCenterTitle.text = "极光分享"
        initClick(this, bd.header.headerBackImg)
    }

    override fun getBinding() = ActivityJiGuangShareBinding.inflate(layoutInflater)
}