package com.yinp.proappkotlin.me

import android.content.Context
import android.graphics.Color
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityAddLabelBinding
import com.yinp.proappkotlin.utils.StatusBarUtil
import com.yinp.proappkotlin.utils.ViewPager2Utils
import com.yinp.proappkotlin.view.SimplePagerTitlePictureView
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import java.util.*

/**
 * @author   :yinpeng
 * date      :2021/10/15
 * package   :com.yinp.proappkotlin.me
 * describe  :
 */
class AddLabelActivity : BaseActivity<ActivityAddLabelBinding>() {
    override fun initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext))
        initClick(this, bd.header.headerBackImg)
//        initIndicator();
    }

    private fun initIndicator() {
//        todayUndeterminedFragment = TodayUndeterminedFragment.getInstance();
//        labelFragment = LabelFragment.getInstance();
//        fragments.put(0, todayUndeterminedFragment);
//        fragments.put(1, labelFragment);
//        bd.materialViewPager.setAdapter(ViewPager2Utils.getAdapter(this, fragments));
        val titleList: List<String> = ArrayList(listOf("今日待做", "标签"))
        bd.materialIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator7 = CommonNavigator(mContext)
        commonNavigator7.isAdjustMode = true
        commonNavigator7.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titleList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitlePictureView(context)
                simplePagerTitleView.text = titleList[index]
                simplePagerTitleView.setNormalColor(resources.getColor(R.color.b8b8b8))
                simplePagerTitleView.textSize = 16f
                when (index) {
                    0 -> {
                        simplePagerTitleView.setmNormalDrawable(R.mipmap.task)
                        simplePagerTitleView.setmSelectedDrawable(R.mipmap.task_s)
                    }
                    1 -> {
                        simplePagerTitleView.setmNormalDrawable(R.mipmap.label)
                        simplePagerTitleView.setmSelectedDrawable(R.mipmap.label_s)
                    }
                }
                simplePagerTitleView.setSelectedColor(resources.getColor(R.color.ff4d4d))
                simplePagerTitleView.setOnClickListener {
                    bd.materialViewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = UIUtil.dip2px(context, 0.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 56.0).toFloat()
                indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(resources.getColor(R.color.fafafa))
                return indicator
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