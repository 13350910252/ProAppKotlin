package com.yinp.proappkotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentTodayUndeterminedBinding

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin
 * describe  :
 */
class TodayUndeterminedFragment : BaseFragment<FragmentTodayUndeterminedBinding>() {
    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?): ViewBinding {
        return FragmentTodayUndeterminedBinding.inflate(inflater, parent, false)
    }
}