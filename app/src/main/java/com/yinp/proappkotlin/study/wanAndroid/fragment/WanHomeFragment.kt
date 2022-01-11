package com.yinp.proappkotlin.study.wanAndroid.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentWanHomeBinding
import com.yinp.proappkotlin.databinding.ItemWanHomeListBinding
import com.yinp.proappkotlin.home.HomeViewModel
import com.yinp.proappkotlin.home.bean.HomeBannerData
import com.yinp.proappkotlin.study.wanAndroid.WanHomeBannerAdapter
import com.yinp.proappkotlin.study.wanAndroid.data.WanHomeBannerData
import com.yinp.proappkotlin.study.wanAndroid.data.WanHomeListData
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import com.yinp.tools.utils.DateUtils
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2021/10/27
 * package   :com.yinp.proappkotlin.study.wanAndroid.fragment
 * describe  :
 */
class WanHomeFragment : BaseFragment<FragmentWanHomeBinding>() {
    private lateinit var bannerAdapter: WanHomeBannerAdapter
    private var listBanner = mutableListOf<HomeBannerData>()

    private val dataList = mutableListOf<WanHomeListData.Data>()
    private lateinit var commonAdapter: CommonAdapter<WanHomeListData.Data>

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private var page = 0

    companion object {
        fun getInstance() = WanHomeFragment().apply {
            arguments = Bundle()
        }
    }

    override fun initViews() {
        initBanner()
        initRecycler()
        getBannerList()
        getStickList()
        refresh()
    }

    private fun initBanner() {
        bannerAdapter = WanHomeBannerAdapter(listBanner, requireContext())
        bd.topBanner.setAdapter(bannerAdapter).addBannerLifecycleObserver(activity).indicator =
            CircleIndicator(
                context
            )
        bd.topBanner.setOnBannerListener(OnBannerListener { data: WanHomeBannerData, position: Int ->
            JumpWebUtils.startWebView(
                requireContext(),
                data.title,
                data.url
            )
        })
    }

    private fun initRecycler() {
        commonAdapter = object : CommonAdapter<WanHomeListData.Data>(requireContext(), dataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): ComViewHolder {
                return ViewHolder(
                    ItemWanHomeListBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                )
            }

            override fun onBindItem(
                holder: RecyclerView.ViewHolder?,
                position: Int,
                item: WanHomeListData.Data
            ) {
                val viewHolder = holder as ViewHolder
                viewHolder.binding.tvTitle.text = item.title

                viewHolder.binding.tvAuthor.text =
                    "作者：${if (item.author.isNullOrEmpty()) item.shareUser else item.author}"
                viewHolder.binding.tvType.text =
                    "分类：${item.superChapterName} ${item.chapterName}"
                if (item.isStick) {
                    viewHolder.binding.tvStick.visibility = View.VISIBLE
                } else {
                    viewHolder.binding.tvStick.visibility = View.GONE
                }
                viewHolder.binding.tvSuperChapter.text = item.superChapterName
                if (item.collect!!) {
                    viewHolder.binding.ivCollect.setImageResource(R.mipmap.collecton_s)
                } else {
                    viewHolder.binding.ivCollect.setImageResource(R.mipmap.collecton)
                }
                val date = DateUtils.toDate(DateUtils.yyyy_MM_dd_HH_mm, item.niceDate)
                if (date == null) {
                    viewHolder.binding.tvDate.text = item.niceDate
                } else {
                    viewHolder.binding.tvDate.text = DateUtils.format(date)
                }
                if (viewHolder.binding.tvDate.text.isNullOrEmpty().not()) {
                    val value: String = viewHolder.binding.tvDate.text.toString().trim()
                    if (value.contains("小时") || value.contains("刚刚")) {
                        viewHolder.binding.tvLatest.visibility = View.VISIBLE
                    } else {
                        viewHolder.binding.tvLatest.visibility = View.GONE
                    }
                } else {
                    viewHolder.binding.tvLatest.visibility = View.GONE
                }
                viewHolder.binding.ivCollect.setOnClickListener { }
            }
        }
        commonAdapter.setOnItemClickListener(object : ComViewHolder.OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                val item: WanHomeListData.Data = dataList[position]
                JumpWebUtils.startWebView(requireContext(), item.title, item.link)
            }
        })
        bd.baseRecycle.layoutManager = LinearLayoutManager(context)
        bd.baseRecycle.adapter = commonAdapter
    }

    internal class ViewHolder(val binding: ItemWanHomeListBinding) :
        ComViewHolder(binding.root)

    private fun refresh() {
        //下拉刷新
        bd.baseRefresh.setRefreshHeader(ClassicsHeader(context))
        bd.baseRefresh.setRefreshFooter(ClassicsFooter(context))
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener {
            page = 0
            getListInfo()
        }
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener {
            page++
            getListInfo()
        }
    }

    private fun getBannerList() {
        viewModel.getBannerList()
        lifecycleScope.launch {
            viewModel.homeBannerData.collect() {
                when (it) {
                    is WanResultDispose.Start -> showLoading("加载中...")
                    is WanResultDispose.Success -> {
                        it.data.data?.let { data ->
                            listBanner.clear()
                            listBanner.addAll(data)
                            bannerAdapter.setDatas(listBanner)
                            bannerAdapter.notifyDataSetChanged()
//                            data[0].ss?.let {
//                                Log.d("abcd", "getBannerList:fsafdafdaff ")
//                            }
                        }
                    }
                }
            }
        }
    }

    private fun getStickList() {
        viewModel.getStickList()
        lifecycleScope.launch {
            viewModel.wanHomeListData.collect {
                when (it) {
                    is WanResultDispose.Success -> {
                        it.data.data?.let { data ->
                            if (page == 0) {
                                dataList.clear()
                                dataList.addAll(data)
                                bd.bottom.noLl.visibility = View.GONE
                                bd.baseRefresh.visibility = View.VISIBLE

                                for (i in dataList.indices) {
                                    dataList[i].isStick = true
                                }
                            }
                        }
                        getListInfo()
                    }
                    is WanResultDispose.CodeError -> {
                    }
                    is WanResultDispose.Error -> {
                    }
                }
            }
        }
    }

    /**
     * 获取首页得数据
     */
    private fun getListInfo() {
        viewModel.getListInfo(page)
        lifecycleScope.launch {
            viewModel.wanHomeListData2.collect {
                when (it) {
                    is WanResultDispose.Success -> {
                        it.data.data?.let { data ->
                            if (page == 0) {
                                bd.baseRefresh.finishRefresh()
                            } else {
                                bd.baseRefresh.finishLoadMore()
                            }
                            if (data.datas.isNullOrEmpty().not()) {
                                val size = dataList.size
                                dataList.addAll(data.datas!!)
                                bd.bottom.noLl.visibility = View.GONE
                                bd.baseRefresh.visibility = View.VISIBLE
                                commonAdapter.notifyItemRangeChanged(size, dataList.size)
                            }
                        }
                        hideLoading()
                    }
                }
            }
        }
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentWanHomeBinding.inflate(inflater, parent, false)
}