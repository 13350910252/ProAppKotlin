package com.yinp.proappkotlin.me

import android.content.Context
import android.graphics.Color
import android.util.SparseArray
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.TodayUndeterminedFragment
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.base.LabelFragment
import com.yinp.proappkotlin.databinding.ActivityAddLabelBinding
import com.yinp.proappkotlin.dpToPx
import com.yinp.proappkotlin.utils.ViewPager2Utils
import com.yinp.proappkotlin.view.SimplePagerTitlePictureView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.proappkotlin.me
 * describe  :
 */
class AddLabelActivity : BaseActivity<ActivityAddLabelBinding>() {
    private val fragments = SparseArray<Fragment>()
    override fun initViews() {
        initClick(bd.header.headerBackImg)
        initIndicator();
    }

    private val todayUndeterminedFragment by lazy {
        TodayUndeterminedFragment.getInstance()
    }
    private val labelFragment by lazy {
        LabelFragment.getInstance()
    }

    private fun initIndicator() {
        fragments.put(0, todayUndeterminedFragment)
        fragments.put(1, labelFragment)
        bd.materialViewPager.adapter = ViewPager2Utils.getAdapter(this, fragments)

        val titleList = mutableListOf("今日待做", "标签")
        bd.materialIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator7 = CommonNavigator(mContext)
        commonNavigator7.isAdjustMode = true
        commonNavigator7.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titleList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return SimplePagerTitlePictureView(context).apply {
                    text = titleList[index]
                    setNormalColor(ContextCompat.getColor(context, R.color.b8b8b8))
                    textSize = 16f
                    when (index) {
                        0 -> {
                            setmNormalDrawable(R.mipmap.task)
                            setmSelectedDrawable(R.mipmap.task_s)
                        }
                        1 -> {
                            setmNormalDrawable(R.mipmap.label)
                            setmSelectedDrawable(R.mipmap.label_s)
                        }
                    }
                    setSelectedColor(ContextCompat.getColor(context, R.color.ff4d4d))
                    setOnClickListener {
                        bd.materialViewPager.currentItem = index
                    }
                }
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    mode = LinePagerIndicator.MODE_EXACTLY
                    lineHeight = 0.0f.dpToPx()
                    lineWidth = 56.0f.dpToPx()
                    roundRadius = 3.0f.dpToPx()
                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2.0f)
                    setColors(ContextCompat.getColor(context, R.color.fafafa))
                }
            }
        }
        bd.materialIndicator.navigator = commonNavigator7
        ViewPager2Utils.bind(bd.materialIndicator, bd.materialViewPager)
    }

    override fun onDestroy() {
        super.onDestroy()
        ViewPager2Utils.unBind(bd.materialViewPager)
    }

    override fun getBinding() = ActivityAddLabelBinding.inflate(layoutInflater)
}