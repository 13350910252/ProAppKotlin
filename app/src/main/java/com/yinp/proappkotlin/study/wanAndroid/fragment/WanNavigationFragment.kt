package com.yinp.proappkotlin.study.wanAndroid.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentWanNavigationBinding
import com.yinp.proappkotlin.databinding.ItemSystemOneBinding
import com.yinp.proappkotlin.databinding.ItemSystemTwoBinding
import com.yinp.proappkotlin.study.wanAndroid.data.NavigationListData
import com.yinp.proappkotlin.study.wanAndroid.model.WanNavigationModel
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import com.yinp.tools.adapter.SingleViewHolder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2021/10/29
 * package   :com.yinp.proappkotlin.study.wanAndroid.fragment
 * describe  :
 */
class WanNavigationFragment : BaseFragment<FragmentWanNavigationBinding>() {

    private lateinit var mAdapter: CommonAdapter<NavigationData>
    private val mDataList = mutableListOf<NavigationData>()

    private val viewModel by lazy {
        ViewModelProvider(this)[WanNavigationModel::class.java]
    }

    companion object {
        fun getInstance() = WanNavigationFragment().apply {
            arguments = Bundle()
        }
    }

    override fun initViews() {
        initRecycler()
        viewModel.getNavigationList()
        initData()
    }

    private fun initRecycler() {
        bd.bottom.noLl.visibility = View.GONE
        mAdapter = object : CommonAdapter<NavigationData>(requireContext(), mDataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): SingleViewHolder {
                return if (viewType == 0) {
                    SingleViewHolder(
                        ItemSystemOneBinding.inflate(
                            mInflater,
                            parent,
                            false
                        )
                    )
                } else {
                    SingleViewHolder(
                        ItemSystemTwoBinding.inflate(
                            mInflater,
                            parent,
                            false
                        )
                    )
                }
            }

            override fun getItemViewType(position: Int): Int {
                return if (TextUtils.isEmpty(mDataList[position].title)) {
                    0
                } else {
                    1
                }
            }

            override fun onBindItem(holder: SingleViewHolder, position: Int, item: NavigationData) {
                holder.binding.let {
                    when (it) {
                        is ItemSystemOneBinding -> it.run {
                            tvTitle.text = item.name
                        }
                        is ItemSystemTwoBinding -> it.run {
                            stvValue.text = item.title
                            stvValue.setOnClickListener {
                                JumpWebUtils.startWebView(
                                    requireContext(),
                                    item.title,
                                    item.link
                                )
                            }
                        }
                    }
                }
            }
        }
        bd.rvList.layoutManager = FlexboxLayoutManager(context).apply {
            //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
            flexDirection = FlexDirection.ROW
            //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
            flexWrap = FlexWrap.WRAP //按正常方向换行
            //justifyContent 属性定义了项目在主轴上的对齐方式。
            justifyContent = JustifyContent.FLEX_START //交叉轴的起点对齐。
        }
        bd.rvList.adapter = mAdapter
    }

    /**
     * 获取导航列表
     */
    private fun initData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getNavigationList()
                viewModel.navigationListData.collect {
                    when (it) {
                        is WanResultDispose.Start -> showLoading("加载中...")
                        is WanResultDispose.Success -> {
                            it.data.let { data ->
                                mDataList.clear()
                                for (i in data.indices) {
                                    val navigationListBean: NavigationListData = data[i]
                                    mDataList.add(NavigationData(navigationListBean.name))
                                    if (navigationListBean.articles.isNullOrEmpty().not()) {
                                        for (element in navigationListBean.articles!!) {
                                            mDataList.add(
                                                NavigationData(
                                                    element.title,
                                                    element.link
                                                )
                                            )
                                        }
                                    }
                                }
                                mAdapter.notifyDataSetChanged()
                            }
                            hideLoading()
                        }
                        is WanResultDispose.Error -> {
                            hideLoading()
                            showToast(it.msg)
                        }
                    }
                }
            }
        }
    }

    internal class NavigationData {
        var name: String? = null
        var title: String? = null
        var link: String? = null

        constructor(name: String?) {
            this.name = name
        }

        constructor(title: String?, link: String?) {
            this.title = title
            this.link = link
        }
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentWanNavigationBinding.inflate(inflater, parent, false)
}