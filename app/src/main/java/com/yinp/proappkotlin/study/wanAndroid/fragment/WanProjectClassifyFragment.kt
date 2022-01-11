package com.yinp.proappkotlin.study.wanAndroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentWanProjectClassifyBinding

/**
 * @author   :yinpeng
 * date      :2022/1/10
 * package   :com.yinp.proappkotlin.study.wanAndroid.fragment
 * describe  :
 */
class WanProjectClassifyFragment : BaseFragment<FragmentWanProjectClassifyBinding>() {

    companion object {
        fun getInstance() = WanProjectClassifyFragment().apply {
            arguments = Bundle()
        }
    }

    override fun initViews() {

    }

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = FragmentWanProjectClassifyBinding.inflate(inflater, parent, false)
}