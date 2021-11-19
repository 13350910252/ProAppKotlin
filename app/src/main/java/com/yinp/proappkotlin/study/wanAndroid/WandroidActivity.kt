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
import com.yinp.proappkotlin.databinding.ActivityWandroidBinding
import com.yinp.proappkotlin.study.wanAndroid.fragment.*
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
        initClick(this, bd.header.headerBackImg, bd.header.headerEnd, bd.ivMe)
        initIndicator()
    }

    private fun initIndicator() {
        fragments.put(0, WanHomeFragment.getInstance())
        fragments.put(1, WanSquareFragment.getInstance())
        fragments.put(2, WanNavigationFragment.getInstance())
        fragments.put(3, WanQuestionAnswerFragment.getInstance())
        fragments.put(4, WanSystemFragment.getInstance())
        fragments.put(5, WanProjectFragment.getInstance())
//        fragments.put(6, WanOfficialAccountFragment.getInstance())
//        fragments.put(7, WanProjectClassifyFragment.getInstance())
        bd.materialViewPager.adapter = ViewPager2Utils.getAdapter(this, fragments)
        val titleList: List<String> =
            ArrayList(listOf("首页", "广场", "导航", "问答", "体系", "项目", "公众号", "项目分类"))
        bd.materialIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator7 = CommonNavigator(mContext)
        commonNavigator7.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titleList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitlePictureView(context)
                simplePagerTitleView.text = titleList[index]
                simplePagerTitleView.setNormalColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.b8b8b8
                    )
                )
                simplePagerTitleView.textSize = 16f
                simplePagerTitleView.setSelectedColor(
                    ContextCompat.getColor(
                        mContext, R.color.ff4d4d
                    )
                )
                simplePagerTitleView.setOnClickListener {
                    bd.materialViewPager.setCurrentItem(
                        index,
                        false
                    )
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
                indicator.setColors(resources.getColor(R.color.ff4d4d))
                return indicator
            }
        }
        bd.materialIndicator.navigator = commonNavigator7
        ViewPager2Utils.bind(bd.materialIndicator, bd.materialViewPager)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        if (v === bd.header.headerBackImg) {
            finish()
        } else if (v === bd.ivMe) {
//            if (AppUtils.isLogin(mContext)) {
//                goToActivity(WanMeActivity::class.java)
//            } else {
//                DialogShow.setLoginDialog(
//                    mContext, presenter, true,
//                    WanMeActivity::class.java, supportFragmentManager
//                )
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ViewPager2Utils.unBind(bd.materialViewPager)
    }

    override fun getBinding(): ActivityWandroidBinding {
        return ActivityWandroidBinding.inflate(layoutInflater)
    }
}