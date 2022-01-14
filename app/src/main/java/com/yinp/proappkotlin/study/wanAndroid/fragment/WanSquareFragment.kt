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
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentWanSquareBinding
import com.yinp.proappkotlin.databinding.ItemWanSquareBinding
import com.yinp.proappkotlin.study.wanAndroid.data.WanSquareListData
import com.yinp.proappkotlin.study.wanAndroid.model.WanSquareViewModel
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2021/10/28
 * package   :com.yinp.proappkotlin.study.wanAndroid.fragment
 * describe  :
 */
class WanSquareFragment : BaseFragment<FragmentWanSquareBinding>() {

    private val mDataList = mutableListOf<WanSquareListData.Data>()
    private lateinit var mAdapter: CommonAdapter<WanSquareListData.Data>
    private var mLoad = true

    private val viewModel by lazy {
        ViewModelProvider(this)[WanSquareViewModel::class.java]
    }
    private var mPage = 0

    companion object {
        fun getInstance() = WanSquareFragment().apply {
            arguments = Bundle()
        }
    }

    override fun initViews() {
        initRecycler()
        refresh()
        viewModel.getSquareList(mPage)
        initData()
    }

    private fun initRecycler() {
        mAdapter = object : CommonAdapter<WanSquareListData.Data>(requireContext(), mDataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): ComViewHolder {
                return ViewHolder(
                    ItemWanSquareBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                )
            }

            override fun onBindItem(
                holder: RecyclerView.ViewHolder?,
                position: Int,
                item: WanSquareListData.Data
            ) {
                val viewHolder = holder as ViewHolder
                if (item.fresh) {
                    viewHolder.binding.tvLatest.visibility = View.VISIBLE
                } else {
                    viewHolder.binding.tvLatest.visibility = View.GONE
                }
                viewHolder.binding.ivCollect.isSelected = item.collect
                viewHolder.binding.tvTitle.text = item.title
                val person = if (item.author.isEmpty()) {
                    item.shareUser.ifEmpty { "暂无" }
                } else {
                    item.author
                }
                viewHolder.binding.tvSharePerson.text = "分享人：$person"
                viewHolder.binding.tvTime.text = item.niceDate
            }
        }
        mAdapter.setOnItemClickListener(object : ComViewHolder.OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                val item = mDataList[position]
                if (item.link.isNotEmpty()) {
                    JumpWebUtils.startWebView(requireContext(), item.title, item.link)
                }
            }
        })
        bd.baseRecycle.layoutManager = LinearLayoutManager(context)
        bd.baseRecycle.setHasFixedSize(true)
        bd.baseRecycle.adapter = mAdapter
    }

    internal class ViewHolder(val binding: ItemWanSquareBinding) : ComViewHolder(binding.root)

    private fun refresh() {
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener {
            mPage = 0
            mLoad = false
            viewModel.getSquareList(mPage)
        }
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener {
            mLoad = false
            viewModel.getSquareList(++mPage)
        }
    }

    /**
     * 获取广场列表
     */
    private fun initData() {
        lifecycleScope.launch {
            viewModel.wanSquareListData.collect {
                when (it) {
                    is WanResultDispose.Start -> if (mLoad) showLoading("加载中...")
                    is WanResultDispose.Success -> {
                        Log.d("abcd", "getSquareList: ")
                        it.data.let { data ->
                            if (data.datas.isNotEmpty()) {
                                val length = mDataList.size
                                if (mPage == 0) {
                                    mDataList.clear()
                                    mDataList.addAll(data.datas)
                                    mAdapter.notifyDataSetChanged()
                                    bd.baseRefresh.finishRefresh(true)
                                } else {
                                    mDataList.addAll(data.datas)
                                    mAdapter.notifyItemRangeChanged(length - 1, data.datas.size)
                                    bd.baseRefresh.finishLoadMore(true)
                                }
                                bd.baseRefresh.visibility = View.VISIBLE
                                bd.bottom.noLl.setVisibility(View.GONE)
                            } else {
                                if (mPage == 0) {
                                    bd.baseRefresh.finishRefresh(false)
                                } else {
                                    bd.baseRefresh.finishLoadMore(false)
                                }
                            }
                        } ?: let {
                            if (mPage == 0) {
                                bd.baseRefresh.visibility = View.GONE
                                bd.bottom.noLl.visibility = View.VISIBLE
                            } else {
                                bd.baseRefresh.finishLoadMore(false)
                            }
                        }
                        if (mLoad)
                            hideLoading()
                    }
                }
            }
        }
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentWanSquareBinding.inflate(inflater, parent, false)
}