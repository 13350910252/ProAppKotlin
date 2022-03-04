package com.yinp.proappkotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yinp.proappkotlin.databinding.FragmentLabelBinding

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.base
 * describe  :
 */
class LabelFragment : BaseFragment<FragmentLabelBinding>() {
    companion object {
        fun getInstance(): LabelFragment {
            return LabelFragment().apply {
                arguments = Bundle()
            }
        }
    }

    override fun initViews() {
        initClick( bd.noData, bd.noTips)
        bd.noTips.text = "暂无标签，点击添加"
    }

    override fun onClick(v: View?) {
        when (v) {
            bd.noData,
            bd.noTips -> {
            }
        }
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentLabelBinding.inflate(inflater, parent, false)
}