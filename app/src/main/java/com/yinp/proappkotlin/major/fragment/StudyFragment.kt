package com.yinp.proappkotlin.major.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentStudyBinding
import com.yinp.proappkotlin.databinding.ItemTwoSelectBinding
import com.yinp.proappkotlin.study.jiguang.JiGuangLoginActivity
import com.yinp.proappkotlin.study.jiguang.JiGuangShareActivity
import com.yinp.proappkotlin.study.wanAndroid.WandroidActivity
import com.yinp.tools.fragment_dialog.BaseDialogFragment
import com.yinp.tools.fragment_dialog.CommonDialogFragment
import com.yinp.tools.fragment_dialog.DialogFragmentHolder
import com.yinp.tools.fragment_dialog.ViewConvertListener
import com.yinp.tools.view.TwoSelectView

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class StudyFragment : BaseFragment<FragmentStudyBinding>() {
    override fun initViews() {
        initClick(bd.stvOne, bd.stvTwo, bd.stvThree, bd.stvFour, bd.stvFive, bd.stvSix)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            bd.stvOne -> { //玩Android
                gotoActivity<WandroidActivity>()
            }
//            bd.stvTwo -> { //学习内容总结
//                goToActivity(StudySummarizeActivity::class.java)
//            }
//            bd.stvThree -> { //视频
//            }
//            bd.stvFour -> { //自定义view
//                goToActivity(CustomViewActivity::class.java)
//            }
//            bd.stvFive -> {
//                goToActivity(MyWorkActivity::class.java)
//            }
            bd.stvSix -> {
                initTwoSelect()
            }
        }
    }

    private var mDialogFragment: CommonDialogFragment? = null

    private fun initTwoSelect() {
        CommonDialogFragment.newInstance(context).setLayout(
            ItemTwoSelectBinding.inflate(
                LayoutInflater.from(
                    context
                ), null, false
            )
        )
            .setViewConvertListener(object : ViewConvertListener() {
                override fun convertView(
                    holder: DialogFragmentHolder,
                    dialogFragment: BaseDialogFragment,
                    viewBinding: ViewBinding
                ) {
                    mDialogFragment = dialogFragment as CommonDialogFragment
                    (viewBinding as? ItemTwoSelectBinding)?.tsvSelect?.setcLickListener(object : TwoSelectView.CLickListener() {
                        override fun click(isLeft: Boolean) {
                            if (isLeft) {
                                gotoActivity<JiGuangLoginActivity>()
                            } else {
                                gotoActivity<JiGuangShareActivity>()
                            }
                        }
                    })
                }
            }).setAnimStyle(R.style.CenterDialogAnimation).setGravity(BaseDialogFragment.CENTER)
            .setPercent(false, false).show(childFragmentManager)
    }

    override fun onPause() {
        super.onPause()
        mDialogFragment = mDialogFragment?.run {
            dismiss()
            null
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentStudyBinding.inflate(inflater, parent, false)
}