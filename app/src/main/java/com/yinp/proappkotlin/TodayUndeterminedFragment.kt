package com.yinp.proappkotlin

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentTodayUndeterminedBinding
import com.yinp.proappkotlin.utils.ViewPager2Utils
import com.yinp.tools.view.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import java.util.*

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin
 * describe  :
 */
class TodayUndeterminedFragment : BaseFragment<FragmentTodayUndeterminedBinding>() {
    companion object {
        fun getInstance(): TodayUndeterminedFragment {
            return TodayUndeterminedFragment().apply {
                val bundle = Bundle()
                arguments = bundle
            }
        }
    }

    override fun initViews() {
        initIndicator()
    }

    private val toDoCurFragment by lazy {
        ToDoCurFragment.getInstance()
    }
    private val toDoHistoryFragment by lazy {
        ToDoHistoryFragment.getInstance()
    }
    private val fragments = SparseArray<Fragment>().apply {
        put(0, toDoCurFragment)
        put(0, toDoHistoryFragment)
    }

    private fun initIndicator() {
        bd.materialViewPager.adapter = ViewPager2Utils.getAdapter(this, fragments)
        val titleList: List<String> = ArrayList(listOf("当前", "历史"))
        bd.materialIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator7 = CommonNavigator(context)
        commonNavigator7.isAdjustMode = true
        commonNavigator7.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titleList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView = ColorFlipPagerTitleView(context)
                simplePagerTitleView.text = titleList[index]
                simplePagerTitleView.normalColor =
                    ContextCompat.getColor(requireContext(), R.color._999)
                simplePagerTitleView.selectedColor =
                    ContextCompat.getColor(requireContext(), R.color.ff4d4d)
                simplePagerTitleView.textSize = 14f
                simplePagerTitleView.setOnClickListener { v: View? ->
                    bd.materialViewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = UIUtil.dip2px(context, 4.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 56.0).toFloat()
                indicator.roundRadius = UIUtil.dip2px(context, 2.0).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(ContextCompat.getColor(context, R.color.ff4d4d))
                return indicator
            }
        }
        bd.materialIndicator.navigator = commonNavigator7
        ViewPager2Utils.bind(bd.materialIndicator, bd.materialViewPager)
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?): ViewBinding {
        return FragmentTodayUndeterminedBinding.inflate(inflater, parent, false)
    }
}