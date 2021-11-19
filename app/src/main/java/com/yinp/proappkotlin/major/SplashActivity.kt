package com.yinp.proappkotlin.major

//import com.airbnb.lottie.LottieAnimationView
import android.animation.Animator
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivitySplashBinding
import com.yinp.proappkotlin.utils.AppUtils

/**
 * @Author: yinp
 * @Date: 2021/10/9
 * @Description:启动页
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun initViews() {
        AppUtils.setFullScreen(window)
        initSomething()
    }

    private fun initSomething() {
        bd.lavContent.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                goToActivity(MajorActivity::class.java)
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
    }

    override fun getBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

}