package com.yinp.proappkotlin.major

import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentRecreationBinding

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class RecreationFragment : BaseFragment<FragmentRecreationBinding>() {
    override fun getBinding(): ViewBinding {
        return FragmentRecreationBinding.inflate(layoutInflater)
    }

}