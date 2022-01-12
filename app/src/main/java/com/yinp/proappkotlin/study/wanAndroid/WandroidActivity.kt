package com.yinp.proappkotlin.study.wanAndroid

import android.content.Context
import android.graphics.Color
import android.util.SparseArray
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.base.goToActivity
import com.yinp.proappkotlin.databinding.ActivityWandroidBinding
import com.yinp.proappkotlin.study.wanAndroid.dialog.DialogShow
import com.yinp.proappkotlin.study.wanAndroid.fragment.*
import com.yinp.proappkotlin.utils.AppUtils
import com.yinp.proappkotlin.utils.StatusBarUtil
import com.yinp.proappkotlin.utils.ViewPager2Utils
import com.yinp.proappkotlin.view.SimplePagerTitlePictureView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @author   :yinpeng
 * date      :2021/10/18
 * package   :com.yinp.proappkotlin.study.wanAndroid
 * describe  :
 */
class WandroidActivity : BaseActivity<ActivityWandroidBinding>() {
    private val fragments = SparseArray<Fragment>()

    override fun initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(this))
        bd.header.headerCenterTitle.text = "玩Android"
        bd.header.headerEnd.setImageResource(R.mipmap.common_software)
        initClick(bd.header.headerBackImg, bd.header.headerEnd, bd.ivMe)
        initIndicator()
    }

    private fun initIndicator() {
        fragments.put(0, WanHomeFragment.getInstance())
        fragments.put(1, WanSquareFragment.getInstance())
        fragments.put(2, WanNavigationFragment.getInstance())
        fragments.put(3, WanQuestionAnswerFragment.getInstance())
        fragments.put(4, WanSystemFragment.getInstance())
        fragments.put(5, WanProjectFragment.getInstance())
        fragments.put(6, WanOfficialAccountFragment.getInstance())
        fragments.put(7, WanProjectClassifyFragment.getInstance())
        bd.materialViewPager.adapter = ViewPager2Utils.getAdapter(this, fragments)
        val titleList = mutableListOf("首页", "广场", "导航", "问答", "体系", "项目", "公众号", "项目分类")
        bd.materialIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator7 = CommonNavigator(mContext)
        commonNavigator7.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titleList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return SimplePagerTitlePictureView(context).apply {
                    text = titleList[index]
                    setNormalColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.b8b8b8
                        )
                    )
                    textSize = 16f
                    setSelectedColor(
                        ContextCompat.getColor(
                            mContext, R.color.ff4d4d
                        )
                    )
                    setOnClickListener {
                        bd.materialViewPager.setCurrentItem(
                            index,
                            false
                        )
                    }
                }
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    mode = LinePagerIndicator.MODE_EXACTLY
                    lineHeight = AppUtils.dpToPx(context, 0.0f)
                    lineWidth = AppUtils.dpToPx(context, 56f)
                    roundRadius = AppUtils.dpToPx(context, 3.0f)
                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2.0f)
                    setColors(ContextCompat.getColor(context, R.color.ff4d4d))
                }
            }
        }
        bd.materialIndicator.navigator = commonNavigator7
        ViewPager2Utils.bind(bd.materialIndicator, bd.materialViewPager)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v) {
            bd.header.headerBackImg -> finish()
            bd.ivMe -> {
                if (AppUtils.isLogin(mContext)) {
                    goToActivity<WanMeActivity>()
                } else {
                    DialogShow.setLoginDialog<WanMeActivity>(
                        mActivity, true,
                        supportFragmentManager
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ViewPager2Utils.unBind(bd.materialViewPager)
    }

    override fun getBinding() = ActivityWandroidBinding.inflate(layoutInflater)
}