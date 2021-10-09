package com.yinp.proappkotlin

import android.animation.Animator
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragmentActivity
import com.yinp.proappkotlin.databinding.ActivitySplashBinding

/**
 * @Author: yinp
 * @Date: 2021/10/9
 * @Description:启动页
 */
class SplashActivity : BaseFragmentActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.setFullScreen(window)
        initSomething()
    }

    fun initSomething() {
        bd.lavContent.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                goToActivity(MajorActivity::class.java)
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
    }

    override fun getBinding(): ViewBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }
}