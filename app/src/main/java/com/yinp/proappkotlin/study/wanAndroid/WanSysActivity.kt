package com.yinp.proappkotlin.study.wanAndroid

import android.view.View
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityWanSysBinding
import com.yinp.proappkotlin.utils.StatusBarUtil

/**
 * @author   :yinpeng
 * date      :2022/1/12
 * package   :com.yinp.proappkotlin.study.wanAndroid
 * describe  :从体系过来得
 */
class WanSysActivity : BaseActivity<ActivityWanSysBinding>() {
    override fun initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext))
        initClick(bd.header.headerBackImg)
        bindData()
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v) {
            bd.header.headerBackImg -> finish()
        }
    }

    override fun getBinding() = ActivityWanSysBinding.inflate(layoutInflater)
}