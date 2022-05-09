package com.yinp.proappkotlin.major

import android.view.View
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityLoginBinding

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun initViews() {
        initClick(bd.stvLogin)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v) {
            bd.stvLogin -> gotoActivity<MajorActivity>()
        }
    }

    override fun getBinding() = ActivityLoginBinding.inflate(layoutInflater)
}