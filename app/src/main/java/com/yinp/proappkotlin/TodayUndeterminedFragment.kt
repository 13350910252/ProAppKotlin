package com.yinp.proappkotlin

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentTodayUndeterminedBinding
import com.yinp.proappkotlin.utils.AppUtils
import com.yinp.proappkotlin.utils.ViewPager2Utils
import com.yinp.tools.view.ColorFlipPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin
 * describe  :
 */
class TodayUndeterminedFragment : BaseFragment<FragmentTodayUndeterminedBinding>() {
    companion object {
        fun getInstance() = TodayUndeterminedFragment().apply {
            arguments = Bundle()
        }
    }

    override fun initViews() {
        initIndicator()
    }

    public val toDoCurFragment by lazy {
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
        val titleList = mutableListOf("当前", "历史")
        bd.materialIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator7 = CommonNavigator(context)
        commonNavigator7.isAdjustMode = true
        commonNavigator7.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titleList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return ColorFlipPagerTitleView(context).apply {
                    text = titleList[index]
                    normalColor = ContextCompat.getColor(requireContext(), R.color._999)
                    selectedColor = ContextCompat.getColor(requireContext(), R.color.ff4d4d)
                    textSize = 14f
                    setOnClickListener { bd.materialViewPager.currentItem = index }
                }
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    mode = LinePagerIndicator.MODE_EXACTLY
                    lineHeight = AppUtils.dpToPx(4.0f)
                    lineWidth = AppUtils.dpToPx(56f)
                    roundRadius = AppUtils.dpToPx(2f)
                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2.0f)
                    setColors(ContextCompat.getColor(context, R.color.ff4d4d))
                }
            }
        }
        bd.materialIndicator.navigator = commonNavigator7
        ViewPager2Utils.bind(bd.materialIndicator, bd.materialViewPager)
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentTodayUndeterminedBinding.inflate(inflater, parent, false)
}