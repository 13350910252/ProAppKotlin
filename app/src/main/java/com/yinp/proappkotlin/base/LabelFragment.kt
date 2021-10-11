package com.yinp.proappkotlin.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.databinding.FragmentLabelBinding

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.base
 * describe  :
 */
class LabelFragment : BaseFragment<FragmentLabelBinding>() {
    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?): ViewBinding {
        return FragmentLabelBinding.inflate(inflater, parent, false)
    }
}