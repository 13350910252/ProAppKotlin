package com.yinp.proappkotlin

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragmentActivity
import com.yinp.proappkotlin.databinding.ActivityMajorBinding

/**
 * @Author: yinp
 * @Date: 2021/10/9
 * @Description:首页
 */
class MajorActivity : BaseFragmentActivity<ActivityMajorBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getBinding(): ViewBinding {
        return ActivityMajorBinding.inflate(layoutInflater)
    }
}