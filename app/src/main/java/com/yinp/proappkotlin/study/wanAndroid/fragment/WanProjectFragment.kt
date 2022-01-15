package com.yinp.proappkotlin.study.wanAndroid.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentWanProjectBinding
import com.yinp.proappkotlin.databinding.ItemWanProjectBinding
import com.yinp.proappkotlin.launchAndCollectIn
import com.yinp.proappkotlin.study.wanAndroid.data.WanProjectListData
import com.yinp.proappkotlin.study.wanAndroid.model.WanProjectViewModel
import com.yinp.proappkotlin.utils.AppUtils
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter

/**
 * @author   :yinpeng
 * date      :2021/11/2
 * package   :com.yinp.proappkotlin.study.wanAndroid.fragment
 * describe  :
 */
class WanProjectFragment : BaseFragment<FragmentWanProjectBinding>() {
    private val mDataList = mutableListOf<WanProjectListData.Data>()
    private lateinit var mAdapter: CommonAdapter<WanProjectListData.Data>
    private var mPage = 0
    private var mLoad = true

    private val viewModel by lazy {
        ViewModelProvider(this)[WanProjectViewModel::class.java]
    }

    companion object {
        fun getInstance() = WanProjectFragment().apply {
            arguments = Bundle()
        }
    }

    override fun initViews() {
        initRecycler()
        refresh()
        viewModel.getProjectList(mPage)
        initData()
    }

    private fun initRecycler() {
        mAdapter = object : CommonAdapter<WanProjectListData.Data>(requireContext(), mDataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): ComViewHolder {
                val itemWanProjectBinding: ItemWanProjectBinding = ItemWanProjectBinding.inflate(
                    layoutInflater, parent, false
                )
                return ViewHolder(itemWanProjectBinding)
            }

            override fun onBindItem(
                holder: RecyclerView.ViewHolder?,
                position: Int,
                item: WanProjectListData.Data
            ) {
                val viewHolder = holder as ViewHolder
                viewHolder.binding.tvTitle.text = AppUtils.getValue(item.title)
                viewHolder.binding.tvContent.text = AppUtils.getValue(item.desc)
                viewHolder.binding.tvAuthor.text =
                    "作者：" + if (item.author.isNullOrEmpty()) item.shareUser else item.author
                viewHolder.binding.tvDate.text = AppUtils.getValue(item.niceShareDate)
            }
        }
        mAdapter.setOnItemClickListener(object : ComViewHolder.OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                JumpWebUtils.startWebView(
                    requireContext(),
                    mDataList[position].title,
                    mDataList[position].link
                )
            }
        })
        bd.baseRecycle.layoutManager = LinearLayoutManager(context)
        bd.baseRecycle.adapter = mAdapter
    }

    private fun refresh() {
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener {
            mPage = 0
            mLoad = false
            viewModel.getProjectList(mPage)
        }
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener {
            mLoad = false
            viewModel.getProjectList(++mPage)
        }
    }

    /**
     * 获取项目列表
     */
    private fun initData() {
        viewModel.wanProjectListData.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            when (it) {
                is WanResultDispose.Start -> if (mLoad) showLoading("加载中...")
                is WanResultDispose.Success -> {
                    if (mLoad) {
                        hideLoading()
                    }
                    it.data.let { data ->
                        if (data.datas.isNullOrEmpty().not()) {
                            val length = mDataList.size
                            if (mPage == 0) {
                                mDataList.clear()
                                mDataList.addAll(data.datas!!)
                                mAdapter.notifyDataSetChanged()
                                bd.baseRefresh.finishRefresh(true)
                            } else {
                                mDataList.addAll(data.datas!!)
                                mAdapter.notifyItemRangeChanged(length - 1, mDataList.size)
                                bd.baseRefresh.finishLoadMore(true)
                            }
                            bd.baseRefresh.visibility = View.VISIBLE
                            bd.bottom.noLl.visibility = View.GONE
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
                }
                is WanResultDispose.Error -> Log.d("abcd", "getProjectList:aaaaa ")
            }
        }
    }

    internal class ViewHolder(val binding: ItemWanProjectBinding) : ComViewHolder(binding.root)

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentWanProjectBinding.inflate(inflater, parent, false)
}