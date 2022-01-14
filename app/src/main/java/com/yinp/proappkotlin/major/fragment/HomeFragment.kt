package com.yinp.proappkotlin.major.fragment

import android.content.Context
import android.graphics.Color
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.TodayUndeterminedFragment
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.base.LabelFragment
import com.yinp.proappkotlin.base.adapter.HomeBannerAdapter
import com.yinp.proappkotlin.databinding.FragmentHomeBinding
import com.yinp.proappkotlin.home.HomeViewModel
import com.yinp.proappkotlin.home.bean.HomeBannerData
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.utils.ViewPager2Utils
import com.yinp.proappkotlin.view.SimplePagerTitlePictureView
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.utils.ToastUtil
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @author   :yinpeng
 * date      :2021/10/9
 * package   :com.yinp.proappkotlin
 * describe  :
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val listBanner = mutableListOf<HomeBannerData>()
    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    /**
     * 初始化banner
     */
    private val bannerAdapter by lazy {
        HomeBannerAdapter(listBanner, requireContext())
    }
    private val todayUndeterminedFragment by lazy {
        TodayUndeterminedFragment.getInstance()
    }
    private val labelFragment by lazy {
        LabelFragment.getInstance()
    }
    private val fragments = SparseArray<Fragment>()

    override fun initViews() {
        initRecycler()
        initIndicator()
        getBannerList()
    }

    private fun initRecycler() {
        bd.topBanner.addBannerLifecycleObserver(activity)
            .isAutoLoop(true).indicator =
            CircleIndicator(context)
        bd.topBanner.setOnBannerListener(OnBannerListener { data: HomeBannerData, position: Int ->
            JumpWebUtils.startWebView(
                requireContext(),
                data.title,
                data.url
            )
        })
    }

    private fun initIndicator() {
        fragments.put(0, todayUndeterminedFragment)
        fragments.put(1, labelFragment)
        bd.materialViewPager.adapter = ViewPager2Utils.getAdapter(this, fragments)
        val titleList = mutableListOf("今日待做", "标签")
        bd.materialIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator7 = CommonNavigator(context)
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
                    setSelectedColor(
                        ContextCompat.getColor(context, R.color.ff4d4d)
                    )
                    setOnClickListener {
                        bd.materialViewPager.currentItem = index
                    }
                }
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    mode = LinePagerIndicator.MODE_EXACTLY
                    lineHeight = UIUtil.dip2px(context, 0.0).toFloat()
                    lineWidth = UIUtil.dip2px(context, 56.0).toFloat()
                    roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2.0f)
                    setColors(ContextCompat.getColor(context, R.color.fafafa))
                }
            }
        }
        bd.materialIndicator.navigator = commonNavigator7
        ViewPager2Utils.bind(bd.materialIndicator, bd.materialViewPager)
    }

    private fun getBannerList() {
        viewModel.getBannerList()
        lifecycleScope.launch {
            viewModel.homeBannerData.collect {
                when (it) {
                    is WanResultDispose.Start -> showLoading("加载中...")
                    is WanResultDispose.Success -> {
                        it.data.let { data ->
                            listBanner.clear()
                            listBanner.addAll(data)
                            bd.topBanner.setAdapter(bannerAdapter)
                        }
                        hideLoading()
                    }
                    is WanResultDispose.Error -> {
                        hideLoading()
                        ToastUtil.initToast(requireContext(), it.msg)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ViewPager2Utils.unBind(bd.materialViewPager)
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, parent, false)
}