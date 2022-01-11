package com.yinp.proappkotlin.study.wanAndroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentWanOfficialAccountBinding

/**
 * @author   :yinpeng
 * date      :2022/1/10
 * package   :com.yinp.proappkotlin.study.wanAndroid.fragment
 * describe  :
 */
class WanOfficialAccountFragment : BaseFragment<FragmentWanOfficialAccountBinding>() {

    companion object {
        fun getInstance() = WanOfficialAccountFragment().apply {
            arguments = Bundle()
        }
    }

    override fun initViews() {

    }

    override fun getBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = FragmentWanOfficialAccountBinding.inflate(inflater, parent, false)
}