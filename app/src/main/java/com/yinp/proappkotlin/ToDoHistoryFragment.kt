package com.yinp.proappkotlin

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentToDoHistoryBinding
import com.yinp.proappkotlin.databinding.ItemHistoryUndeterminedListBinding
import com.yinp.proappkotlin.room.bean.LocaleTaskBean
import com.yinp.tools.adapter.CommonAdapter
import com.yinp.tools.adapter.SingleViewHolder

/**
 * @author   :yinpeng
 * date      :2021/10/14
 * package   :com.yinp.proappkotlin
 * describe  :
 */
class ToDoHistoryFragment : BaseFragment<FragmentToDoHistoryBinding>() {

    private lateinit var adapter: CommonAdapter<LocaleTaskBean>
    private val dataList = mutableListOf<LocaleTaskBean>()

    override fun initViews() {
        bd.noTips.text = "暂无待做历史任务"
        initRecycler()
        getDataList()
    }

    companion object {
        fun getInstance() = ToDoHistoryFragment().apply {
            arguments = Bundle()
        }
    }

    private fun initRecycler() {
        adapter = object : CommonAdapter<LocaleTaskBean>(requireContext(), dataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): SingleViewHolder {
                return SingleViewHolder(
                    ItemHistoryUndeterminedListBinding.inflate(
                        mInflater,
                        parent,
                        false
                    )
                )
            }

            override fun onBindItem(holder: SingleViewHolder, position: Int, item: LocaleTaskBean) {
                (holder.binding as ItemHistoryUndeterminedListBinding).apply {
                    if (!TextUtils.isEmpty(item.title)) {
                        tvTitle.text = item.title
                        tvTitle.visibility = View.VISIBLE
                    }
                    tvContent.text = item.content
                    tvDate.text = "${item.date} ${item.time}"
                    if (item.isFinish == 1) {
                        tvStatus.text = "已完成"
                        ccvPoint.changeColor(requireContext(), R.color._59d281)
                        tvStatus.setTextColor(
                            ContextCompat.getColor(
                                requireContext(), R.color._59d281
                            )
                        )
                        sllStatus.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(), R.color._1559d281
                            )
                        )
                    } else {
                        tvStatus.text = "未完成"
                        ccvPoint.changeColor(requireContext(), R.color.fb5c5c)
                        tvStatus.setTextColor(
                            ContextCompat.getColor(
                                requireContext(), R.color.fb5c5c
                            )
                        )
                        sllStatus.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(), R.color._15fb5c5c
                            )
                        )
                    }
                }
            }
        }
        bd.rvList.layoutManager = LinearLayoutManager(context)
        //添加了这个，如果从最后开始删除就没有问题，如果随意删除就会崩溃
        bd.rvList.setHasFixedSize(true)
        bd.rvList.adapter = adapter
        bd.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mLinearLayoutManager =
                    mLinearLayoutManager ?: recyclerView.layoutManager as? LinearLayoutManager
                mLinearLayoutManager?.run {
                    mTotalCount = itemCount
                    mVisiblePosition = findLastVisibleItemPosition()
                    if (mCanGet && dy > 0 && mTotalCount % mVisiblePosition == 2) {
                        mCanGet = false
                        page++
                        getDataList()
                    } else if (mTotalCount % mVisiblePosition != 2) {
                        mCanGet = true
                    }
                }

            }
        })
    }

    private var mTotalCount = -1
    private var mVisiblePosition = -1
    private var mLinearLayoutManager: LinearLayoutManager? = null

    //表示是否可以刷新了
    private var mCanGet = false
    private var page = 1

    /**
     * 供外部调用刷新的方法
     */
    fun initDataList() {
        page = 1
        getDataList()
    }

    /**
     * 获取今日数据列表
     */
    private fun getDataList() {
//        val list: List<LocaleTaskBean> = dealLocaleTask.queryHistoryList(page)
//        if (list.isNotEmpty()) {
//            if (page == 1) {
//                dataList.clear()
//            }
//            dataList.addAll(list)
//            adapter.notifyItemRangeInserted(dataList.size, dataList.size + list.size)
//            bd.noLl.visibility = View.GONE
//            bd.rvList.visibility = View.VISIBLE
//        } else {
//            if (dataList.size == 0) {
//                bd.noLl.visibility = View.VISIBLE
//                bd.rvList.visibility = View.GONE
//            }
//        }
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentToDoHistoryBinding.inflate(inflater, parent, false)
}