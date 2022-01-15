package com.yinp.proappkotlin.study.wanAndroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yinp.proappkotlin.base.BaseActivity
import com.yinp.proappkotlin.databinding.ActivityWanCollectionBinding
import com.yinp.proappkotlin.databinding.ItemWanCollectListBinding
import com.yinp.proappkotlin.study.wanAndroid.data.CollectionListBean
import com.yinp.proappkotlin.study.wanAndroid.dialog.DialogShow
import com.yinp.proappkotlin.study.wanAndroid.model.WanCollectModel
import com.yinp.proappkotlin.study.wanAndroid.model.WanUnifyCollectModel
import com.yinp.proappkotlin.utils.AppUtils
import com.yinp.proappkotlin.utils.JumpWebUtils
import com.yinp.proappkotlin.utils.StatusBarUtil
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2022/1/12
 * package   :com.yinp.proappkotlin.study.wanAndroid
 * describe  :收藏列表
 */
class WanCollectionActivity : BaseActivity<ActivityWanCollectionBinding>() {
    private val dataList = mutableListOf<CollectionListBean.Data>()
    private lateinit var commonAdapter: CommonAdapter<CollectionListBean.Data>

    private val viewModel by lazy {
        ViewModelProvider(this)[WanCollectModel::class.java]
    }
    private val collectModel by lazy {
        ViewModelProvider(this)[WanUnifyCollectModel::class.java]
    }

    private var isLoad = true
    private var page = 0
    override fun initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext))
        bd.header.headerCenterTitle.text = "我的收藏"
        initClick(bd.header.headerBackImg)
        initRecycler()
        refresh()
        isLoad = true
        getCollectInfo()
    }

    private fun initRecycler() {
        commonAdapter = object : CommonAdapter<CollectionListBean.Data>(mContext, dataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): ComViewHolder {
                val holder = ViewHolder(
                    ItemWanCollectListBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                )
                holder.binding.ivCollect.setOnClickListener {
                    val position = holder.absoluteAdapterPosition
                    if (AppUtils.isLogin(mContext)) {
                        if (!dataList[position].isCollect) {
                            collectModel.addCollect(datalist[position].originId)
                            lifecycleScope.launch {
                                repeatOnLifecycle(Lifecycle.State.STARTED) {
                                    collectModel.unifyAddCollect.collect {
                                        when (it) {
                                            is WanResultDispose.Success -> {
                                                dataList[position].isCollect = true
                                                commonAdapter.notifyItemChanged(position, "")
                                                showToast("已收藏")
                                            }
                                            is WanResultDispose.Error -> {
                                                showToast("收藏失败")
                                            }
                                        }
                                    }
                                }
                            }
                        } else {

                            collectModel.cancelCollect(datalist[position].originId)
                            lifecycleScope.launch {
                                repeatOnLifecycle(Lifecycle.State.STARTED) {
                                    collectModel.unifyCancelCollect.collect {
                                        when (it) {
                                            is WanResultDispose.Success -> {
                                                dataList[position].isCollect = false
                                                commonAdapter.notifyItemChanged(position, "")
                                                showToast("取消收藏")
                                            }
                                            is WanResultDispose.Error -> {
                                                showToast("取消失败")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        DialogShow.setLoginDialog<WanMeActivity>(
                            mActivity, false, supportFragmentManager
                        )
                    }
                }
                return holder
            }

            override fun onBindItem(
                holder: RecyclerView.ViewHolder?,
                position: Int,
                item: CollectionListBean.Data
            ) {
                val viewHolder = holder as ViewHolder
                viewHolder.binding.tvTitle.text = AppUtils.getValue(item.title)

//                HtmlImageGetter htmlImageGetter = new HtmlImageGetter(viewHolder.binding.htvContent);
//                htmlImageGetter.enableCompressImage(true, 100);
//                viewHolder.binding.htvContent.setHtml(item.getDesc(), htmlImageGetter);
                viewHolder.binding.tvAuthor.text = item.author
                viewHolder.binding.tvTime.text = item.niceDate
                viewHolder.binding.tvChapterName.text = item.chapterName
                viewHolder.binding.ivCollect.isSelected = item.isCollect
            }
        }
        commonAdapter.setOnItemClickListener(object : ComViewHolder.OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                val item = dataList[position]
                JumpWebUtils.startWebView(mContext, item.title, item.link)
            }
        })
        bd.baseRecycle.layoutManager = LinearLayoutManager(mContext)
        bd.baseRecycle.setHasFixedSize(true)
        bd.baseRecycle.adapter = commonAdapter
    }

    internal class ViewHolder(val binding: ItemWanCollectListBinding) : ComViewHolder(binding.root)

    override fun onClick(v: View) {
        super.onClick(v)
        if (v === bd.header.headerBackImg) {
            finish()
        }
    }

    private fun refresh() {
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener {
            page = 0
            isLoad = false
            getCollectInfo()
        }
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener {
            page++
            isLoad = false
            getCollectInfo()
        }
    }

    /**
     * 获取收藏列表
     */
    private fun getCollectInfo() {
        viewModel.getCollectList(page)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectionListBean.collect {
                    when (it) {
                        is WanResultDispose.Start -> {
                            if (isLoad) {
                                showLoading("加载中...")
                            }
                        }
                        is WanResultDispose.Success -> {
                            it.data.let { data ->
                                if (data.datas.isNotEmpty()) {
                                    val start = dataList.size
                                    dataList.addAll(data.datas)
                                    val end = dataList.size
                                    commonAdapter.notifyItemRangeChanged(start - 1, end)
                                    bd.baseRefresh.visibility = View.VISIBLE
                                    bd.bottom.noLl.visibility = View.GONE
                                }
                            } ?: let {
                                bd.baseRefresh.visibility = View.GONE
                                bd.bottom.noLl.visibility = View.VISIBLE
                            }
                            if (isLoad) {
                                hideLoading()
                            }
                        }
                        is WanResultDispose.Error -> {
                            if (page > 0) bd.baseRefresh.finishLoadMore(false)
                            else bd.baseRefresh.finishRefresh(false)
                            hideLoading()
                            showToast(it.msg)
                        }
                    }
                }
            }
        }
    }

    override fun getBinding() = ActivityWanCollectionBinding.inflate(layoutInflater)
}