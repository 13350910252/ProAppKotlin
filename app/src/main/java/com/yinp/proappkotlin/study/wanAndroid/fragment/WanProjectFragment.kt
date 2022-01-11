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
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentWanProjectBinding
import com.yinp.proappkotlin.databinding.ItemWanProjectBinding
import com.yinp.proappkotlin.study.wanAndroid.data.WanProjectListData
import com.yinp.proappkotlin.study.wanAndroid.model.WanProjectViewModel
import com.yinp.proappkotlin.utils.AppUtils
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author   :yinpeng
 * date      :2021/11/2
 * package   :com.yinp.proappkotlin.study.wanAndroid.fragment
 * describe  :
 */
class WanProjectFragment : BaseFragment<FragmentWanProjectBinding>() {
    private val dataList = ArrayList<WanProjectListData.DataX>()
    private lateinit var adapter: CommonAdapter<WanProjectListData.DataX>
    private var page = 0
    var isLoad = true

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
        getProjectList()
    }

    private fun initRecycler() {
        adapter = object : CommonAdapter<WanProjectListData.DataX>(requireContext(), dataList) {
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
                item: WanProjectListData.DataX
            ) {
                val viewHolder = holder as ViewHolder
                viewHolder.binding.tvTitle.text = AppUtils.getValue(item.title)
                viewHolder.binding.tvContent.text = AppUtils.getValue(item.desc)
                viewHolder.binding.tvAuthor.text =
                    "作者：" + if (item.author.isNullOrEmpty()) item.shareUser else item.author
                viewHolder.binding.tvDate.text = AppUtils.getValue(item.niceShareDate)
            }
        }
        adapter.setOnItemClickListener(object : ComViewHolder.OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                JumpWebUtils.startWebView(
                    requireContext(),
                    dataList[position].title,
                    dataList[position].link
                )
            }
        })
        bd.baseRecycle.layoutManager = LinearLayoutManager(context)
        bd.baseRecycle.adapter = adapter
    }

    private fun refresh() {
        //下拉刷新
        bd.baseRefresh.setRefreshHeader(ClassicsHeader(context))
        bd.baseRefresh.setRefreshFooter(ClassicsFooter(context))
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener {
            page = 0
            isLoad = false
            getProjectList()
        }
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener {
            page++
            isLoad = false
            getProjectList()
        }
    }

    /**
     * 获取项目列表
     */
    private fun getProjectList() {
        viewModel.getProjectList(page)
        lifecycleScope.launch {
            viewModel.wanProjectListData.collect() {
                when (it) {
                    is WanResultDispose.Start -> {
                        if (isLoad) {
                            showLoading("加载中...")
                        }
                    }
                    is WanResultDispose.Success -> {
                        if (isLoad) {
                            hideLoading()
                        }
                        it.data.data?.let { data ->
                            if (data.datas.isNullOrEmpty().not()) {
                                val length = dataList.size
                                if (page == 0) {
                                    dataList.clear()
                                    dataList.addAll(data.datas!!)
                                    adapter.notifyDataSetChanged()
                                    bd.baseRefresh.finishRefresh(true)
                                } else {
                                    dataList.addAll(data.datas!!)
                                    adapter.notifyItemRangeChanged(length, dataList.size)
                                    bd.baseRefresh.finishLoadMore(true)
                                }
                                bd.baseRefresh.visibility = View.VISIBLE
                                bd.bottom.noLl.visibility = View.GONE
                            } else {
                                if (page == 0) {
                                    bd.baseRefresh.finishRefresh(false)
                                } else {
                                    bd.baseRefresh.finishLoadMore(false)
                                }
                            }
                        } ?: let {
                            if (page == 0) {
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
    }

    internal class ViewHolder(val binding: ItemWanProjectBinding) : ComViewHolder(binding.root)

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentWanProjectBinding.inflate(inflater, parent, false)
}