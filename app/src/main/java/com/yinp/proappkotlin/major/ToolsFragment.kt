package com.yinp.proappkotlin.major

import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentToolsBinding

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class ToolsFragment : BaseFragment<FragmentToolsBinding>() {
    override fun getBinding(): ViewBinding {
        return FragmentToolsBinding.inflate(layoutInflater)
    }
}