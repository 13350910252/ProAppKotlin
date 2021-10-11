package com.yinp.proappkotlin.major

import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentStudyBinding

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class StudyFragment : BaseFragment<FragmentStudyBinding>() {
    override fun getBinding(): ViewBinding {
        return FragmentStudyBinding.inflate(layoutInflater)
    }
}