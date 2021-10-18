package com.yinp.proappkotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
    companion object {
        fun getInstance(): LabelFragment {
            return LabelFragment().apply {
                val bundle = Bundle()
                arguments = bundle
            }
        }
    }

    override fun initViews() {
        initClick(this, bd.noData, bd.noTips)
        bd.noTips.text = "暂无标签，点击添加"
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            bd.noData,
            bd.noTips -> {
            }
        }
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?): ViewBinding {
        return FragmentLabelBinding.inflate(inflater, parent, false)
    }
}