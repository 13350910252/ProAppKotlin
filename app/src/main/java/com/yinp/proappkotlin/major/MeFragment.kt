package com.yinp.proappkotlin.major

import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentMeBinding

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class MeFragment : BaseFragment<FragmentMeBinding>() {
    override fun getBinding(): ViewBinding {
        return FragmentMeBinding.inflate(layoutInflater)
    }
}