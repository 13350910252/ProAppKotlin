package com.yinp.proappkotlin.study.wanAndroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.constant.HttpUrl
import com.yinp.proappkotlin.databinding.ActivityWanRankBinding
import com.yinp.proappkotlin.databinding.ItemRankListBinding
import com.yinp.proappkotlin.study.wanAndroid.data.RankListBean
import com.yinp.proappkotlin.study.wanAndroid.model.WanRankModel
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.utils.StatusBarUtil
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author   :yinpeng
 * date      :2022/1/11
 * package   :com.yinp.proappkotlin.study.wanAndroid
 * describe  :玩Android积分排行榜
 */
class WanRankActivity : BaseActivity<ActivityWanRankBinding>() {
    private val dataList = mutableListOf<RankListBean.Data>()
    private lateinit var commonAdapter: CommonAdapter<RankListBean.Data>
    private var page = 1
    private var maxCount = 0
    var isLoad = true

    private val viewModel by lazy {
        ViewModelProvider(this)[WanRankModel::class.java]
    }

    override fun initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext))
        bd.header.headerCenterTitle.text = "积分排行榜"
        bd.header.headerEnd.setImageResource(R.mipmap.ic_guize)
        initClick(bd.header.headerBackImg, bd.header.headerEnd)
        initRecycler()
        refresh()
        isLoad = true
        getRankList()
    }

    override fun onClick(v: View) {
        super.onClick(v)
        if (v === bd.header.headerBackImg) {
            finish()
        } else if (v === bd.header.headerEnd) {
            JumpWebUtils.startWebView(mContext, "积分规则", HttpUrl.INTEGRAL_HELP_URL)
        }
    }

    private fun initRecycler() {
        bd.bottom.noLl.visibility = View.GONE
        commonAdapter = object : CommonAdapter<RankListBean.Data>(mContext, dataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): ComViewHolder {
                return ViewHolder(
                    ItemRankListBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
                )
            }

            override fun onBindItem(
                holder: RecyclerView.ViewHolder?,
                position: Int,
                item: RankListBean.Data
            ) {
                (holder as ViewHolder).binding.run {
                    tvIntegral.text = "${item.coinCount}"
                    tvName.text = item.username
                    tvRanking.background = null
                    tvRankingText.text = ""

                    when (item.rank) {
                        "1" -> {
                            tvRanking.visibility = View.VISIBLE
                            tvRankingText.visibility = View.GONE
                            tvRanking.setBackgroundResource(R.mipmap.ic_rank_1)
                            maxCount = item.coinCount
                        }
                        "2" -> {
                            tvRanking.visibility = View.VISIBLE
                            tvRankingText.visibility = View.GONE
                            tvRanking.setBackgroundResource(R.mipmap.ic_rank_2)
                        }
                        "3" -> {
                            tvRanking.visibility = View.VISIBLE
                            tvRankingText.visibility = View.GONE
                            tvRanking.setBackgroundResource(R.mipmap.ic_rank_3)
                        }
                        else -> {
                            tvRanking.visibility = View.GONE
                            tvRankingText.visibility = View.VISIBLE
                            tvRankingText.setText(item.rank)
                        }
                    }
                    if (maxCount == 0) {
                        return
                    }
                    rllMain.setPercentWidth(item.coinCount * 1.0f / maxCount)
                        .start()
                }
            }
        }
        commonAdapter.setOnItemClickListener(object : ComViewHolder.OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {

            }

        })
        bd.baseRecycle.layoutManager = LinearLayoutManager(mContext)
        bd.baseRecycle.setHasFixedSize(true)
        bd.baseRecycle.adapter = commonAdapter
    }

    internal class ViewHolder(var binding: ItemRankListBinding) : ComViewHolder(
        binding.root
    )

    private fun refresh() {
        //为下来刷新添加事件
        bd.baseRefresh.setEnableRefresh(false)
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener {
            page++
            isLoad = false
            getRankList()
        }
    }

    private fun getRankList() {
        viewModel.getIntegralRankList(page)
        lifecycleScope.launch {
            viewModel.rankListBean.collect { it ->
                when (it) {
                    is WanResultDispose.Start -> if (isLoad) showLoading("加载中...")
                    is WanResultDispose.Success -> {
                        it.data.data?.let { data ->
                            if (page > 1) bd.baseRefresh.finishLoadMore()
                            if (data.datas.isNotEmpty()
                            ) {
                                val start = dataList.size
                                dataList.addAll(data.datas)
                                val end = dataList.size
                                commonAdapter.notifyItemRangeChanged(start, end)
                                bd.baseRefresh.visibility = View.VISIBLE
                                bd.bottom.noLl.visibility = View.GONE
                            }
                        } ?: let {
                            bd.baseRefresh.visibility = View.GONE
                            bd.bottom.noLl.visibility = View.VISIBLE
                        }
                        if (isLoad)
                            hideLoading()
                    }
                    is WanResultDispose.CodeError -> {
                        if (isLoad)
                            hideLoading()
//                        showToast(msg)
                    }
                    is WanResultDispose.Error -> {
                        if (isLoad)
                            hideLoading()
//                        showToast(msg)
                    }
                }
            }
        }
    }

    override fun getBinding() = ActivityWanRankBinding.inflate(layoutInflater)
}