package com.yinp.proappkotlin.major

import android.content.Context
import android.graphics.Color
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.TodayUndeterminedFragment
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.base.LabelFragment
import com.yinp.proappkotlin.base.adapter.HomeBannerAdapter
import com.yinp.proappkotlin.base.bean.BannerEntity
import com.yinp.proappkotlin.databinding.FragmentHomeBinding
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.utils.ViewPager2Utils
import com.yinp.proappkotlin.view.SimplePagerTitlePictureView
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import java.lang.reflect.Type
import java.util.*

/**
 * @author   :yinpeng
 * date      :2021/10/9
 * package   :com.yinp.proappkotlin
 * describe  :
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var bannerAdapter: HomeBannerAdapter
    private val listBanner = mutableListOf<BannerEntity>()

    protected fun initViews(view: View?) {
        initRecycler()
        initIndicator()
        getBannerList()
    }

    private fun initRecycler() {
        bannerAdapter = HomeBannerAdapter(listBanner, requireContext())
        bd.topBanner.setAdapter(bannerAdapter).addBannerLifecycleObserver(this).indicator =
            CircleIndicator(
                context
            )
        bd.topBanner.setOnBannerListener(OnBannerListener { data: BannerEntity, position: Int ->
            JumpWebUtils.startWebView(
                requireContext(),
                data.title,
                data.url
            )
        } as OnBannerListener<BannerEntity>?)
    }

    var todayUndeterminedFragment: TodayUndeterminedFragment? = null
    var labelFragment: LabelFragment? = null
    private val fragments = SparseArray<Fragment?>()

    private fun initIndicator() {
        todayUndeterminedFragment = TodayUndeterminedFragment.getInstance()
        labelFragment = LabelFragment.getInstance()
        fragments.put(0, todayUndeterminedFragment)
        fragments.put(1, labelFragment)
        bd.materialViewPager.adapter = ViewPager2Utils.getAdapter(this, fragments)
        val titleList: List<String> = ArrayList(Arrays.asList("今日待做", "标签"))
        bd.materialIndicator.setBackgroundColor(Color.WHITE)
        val commonNavigator7 = CommonNavigator(context)
        commonNavigator7.isAdjustMode = true
        commonNavigator7.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titleList?.size ?: 0
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitlePictureView(context)
                simplePagerTitleView.setText(titleList[index])
                simplePagerTitleView.setNormalColor(resources.getColor(R.color.b8b8b8))
                simplePagerTitleView.setTextSize(16)
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
                simplePagerTitleView.setOnClickListener { v ->
                    bd.materialViewPager.currentItem =
                        index
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

    private fun getBannerList() {
        showLoading("加载中")
        presenter.getBannerList(object : WanObserver<WanData?>() {
            fun onSuccess(o: WanData?) {
                hideLoading()
                if (o == null) {
                    return
                }
                val jsonElement: JsonElement = o.getData()
                if (jsonElement.isJsonNull()) {
                    return
                }
                val type: Type = object : TypeToken<ArrayList<BannerEntity?>?>() {}.getType()
                listBanner = Gson().fromJson(jsonElement, type)
                bannerAdapter.setDatas(listBanner)
                bannerAdapter.notifyDataSetChanged()
            }

            fun onError(msg: String?) {
                hideLoading()
            }

            fun onCodeFail(msg: String?) {
                hideLoading()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ViewPager2Utils.unBind(bd.materialViewPager)
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?): ViewBinding {
        return FragmentHomeBinding.inflate(inflater, parent, false)
    }
}