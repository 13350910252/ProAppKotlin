package com.yinp.proappkotlin.study.wanAndroid

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.yinp.tools.adapter.SingleViewHolder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2022/1/12
 * package   :com.yinp.proappkotlin.study.wanAndroid
 * describe  :收藏列表
 */
class WanCollectionActivity : BaseActivity<ActivityWanCollectionBinding>() {
    private val mDataList = mutableListOf<CollectionListBean.Data>()
    private lateinit var commonAdapter: CommonAdapter<CollectionListBean.Data>

    private val viewModel by lazy {
        ViewModelProvider(this)[WanCollectModel::class.java]
    }
    private val collectModel by lazy {
        ViewModelProvider(this)[WanUnifyCollectModel::class.java]
    }

    private var mLoad = true
    private var page = 0
    private var mFirst = false
    override fun initViews() {
        bd.header.headerCenterTitle.text = "我的收藏"
        initClick(bd.header.headerBackImg)
        initRecycler()
        refresh()
        initData()
        mLoad = true
        viewModel.getCollectList(page)
    }

    private fun initRecycler() {
        commonAdapter = object : CommonAdapter<CollectionListBean.Data>(mContext, mDataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): SingleViewHolder {
                val holder = SingleViewHolder(
                    ItemWanCollectListBinding.inflate(
                        mInflater,
                        parent,
                        false
                    )
                )
                val binding = holder.binding as ItemWanCollectListBinding
                binding.ivCollect.setOnClickListener {
                    val position = holder.absoluteAdapterPosition
                    if (!mFirst) {
                        mFirst = true
                        lifecycleScope.launch {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                collectModel.unifyAddCollect.collect {
                                    when (it) {
                                        is WanResultDispose.Success -> {
                                            mDataList[position].isCollect = true
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

                        lifecycleScope.launch {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                collectModel.unifyCancelCollect.collect {
                                    when (it) {
                                        is WanResultDispose.Success -> {
                                            mDataList[position].isCollect = false
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
                    if (AppUtils.isLogin(mContext)) {
                        if (!mDataList[position].isCollect) {
                            collectModel.addCollect(mDataList[position].originId)
                        } else {
                            collectModel.cancelCollect(mDataList[position].originId)
                        }
                    } else {
                        DialogShow.setLoginDialog<WanMeActivity>(
                            mActivity, false
                        )
                    }
                }
                return holder
            }

            override fun onBindItem(
                holder: SingleViewHolder,
                position: Int,
                item: CollectionListBean.Data
            ) {
                (holder.binding as ItemWanCollectListBinding).apply {
                    tvTitle.text = AppUtils.getValue(item.title)
//                HtmlImageGetter htmlImageGetter = new HtmlImageGetter(htvContent);
//                htmlImageGetter.enableCompressImage(true, 100);
//                htvContent.setHtml(item.getDesc(), htmlImageGetter);
                    tvAuthor.text = item.author
                    tvTime.text = item.niceDate
                    tvChapterName.text = item.chapterName
                    ivCollect.isSelected = item.isCollect
                }
            }
        }
        commonAdapter.setOnItemClickListener(object : ComViewHolder.OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                val item = mDataList[position]
                if (item.link.isNotEmpty()) {
                    JumpWebUtils.startWebView(mContext, item.title, item.link)
                }
            }
        })
        bd.baseRecycle.layoutManager = LinearLayoutManager(mContext)
        bd.baseRecycle.setHasFixedSize(true)
        bd.baseRecycle.adapter = commonAdapter
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v) {
            bd.header.headerBackImg -> finish()
        }
    }

    private fun refresh() {
        //为下来刷新添加事件
        bd.baseRefresh.setOnRefreshListener {
            page = 0
            mLoad = false
            viewModel.getCollectList(page)
        }
        //为上拉加载添加事件
        bd.baseRefresh.setOnLoadMoreListener {
            mLoad = false
            viewModel.getCollectList(++page)
        }
    }

    /**
     * 获取收藏列表
     */
    private fun initData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectionListBean.collect {
                    when (it) {
                        is WanResultDispose.Start -> if (mLoad) showLoading("加载中...")
                        is WanResultDispose.Success -> {
                            it.data.let { data ->
                                if (data.datas.isNotEmpty()) {
                                    val start = mDataList.size
                                    mDataList.addAll(data.datas)
                                    val end = mDataList.size
                                    commonAdapter.notifyItemRangeChanged(start - 1, end)
                                    bd.baseRefresh.visibility = View.VISIBLE
                                    bd.bottom.noLl.visibility = View.GONE
                                }
                            }
                            if (mLoad) hideLoading()

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