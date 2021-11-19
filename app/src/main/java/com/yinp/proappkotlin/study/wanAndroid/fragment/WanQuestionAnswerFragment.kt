package com.yinp.proappkotlin.study.wanAndroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentWanQuestionAnswerBinding

/**
 * @author   :yinpeng
 * date      :2021/10/29
 * package   :com.yinp.proappkotlin.study.wanAndroid.fragment
 * describe  :
 */
class WanQuestionAnswerFragment : BaseFragment<FragmentWanQuestionAnswerBinding>() {
    companion object {
        fun getInstance(): WanQuestionAnswerFragment {
            val wanQuestionAnswerFragment = WanQuestionAnswerFragment()
            val bundle = Bundle()
            wanQuestionAnswerFragment.arguments = bundle
            return wanQuestionAnswerFragment
        }
    }

    override fun initViews() {

    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?): ViewBinding {
        return FragmentWanQuestionAnswerBinding.inflate(inflater, parent, false)
    }
}