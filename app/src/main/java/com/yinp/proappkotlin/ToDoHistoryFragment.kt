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
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import java.util.*

/**
 * @author   :yinpeng
 * date      :2021/10/14
 * package   :com.yinp.proappkotlin
 * describe  :
 */
class ToDoHistoryFragment : BaseFragment<FragmentToDoHistoryBinding>() {

    private lateinit var adapter: CommonAdapter<LocaleTaskBean>
    private val dataList = ArrayList<LocaleTaskBean>()

    override fun initViews() {
        bd.noTips.text = "暂无待历史做任务"
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
            ): ComViewHolder {
                val binding: ItemHistoryUndeterminedListBinding =
                    ItemHistoryUndeterminedListBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                return LocaleTaskViewHolder(binding)
            }

            override fun onBindItem(
                holder: RecyclerView.ViewHolder?,
                position: Int,
                item: LocaleTaskBean
            ) {
                val viewHolder = holder as LocaleTaskViewHolder
                if (!TextUtils.isEmpty(item.title)) {
                    viewHolder.binding.tvTitle.text = item.title
                    viewHolder.binding.tvTitle.visibility = View.VISIBLE
                }
                viewHolder.binding.tvContent.text = item.content
                viewHolder.binding.tvDate.text = item.date.toString() + " " + item.time
                if (item.isFinish == 1) {
                    viewHolder.binding.tvStatus.text = "已完成"
                    viewHolder.binding.ccvPoint.changeColor(requireContext(), R.color._59d281)
                    viewHolder.binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            requireContext(), R.color._59d281
                        )
                    )
                    viewHolder.binding.sllStatus.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(), R.color._1559d281
                        )
                    )
                } else {
                    viewHolder.binding.tvStatus.text = "未完成"
                    viewHolder.binding.ccvPoint.changeColor(requireContext(), R.color.fb5c5c)
                    viewHolder.binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            requireContext(), R.color.fb5c5c
                        )
                    )
                    viewHolder.binding.sllStatus.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(), R.color._15fb5c5c
                        )
                    )
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
                if (linearLayoutManager == null) {
                    linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                }
                if (linearLayoutManager == null) {
                    return
                }
                totalCount = linearLayoutManager!!.itemCount
                visiblePosition = linearLayoutManager!!.findLastVisibleItemPosition()
                if (`is` && dy > 0 && totalCount % visiblePosition == 2) {
                    `is` = false
                    page++
                    getDataList()
                } else if (totalCount % visiblePosition != 2) {
                    `is` = true
                }
            }
        })
    }

    private var totalCount = -1
    private var visiblePosition = -1
    private var linearLayoutManager: LinearLayoutManager? = null
    private var `is` = false
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

    internal class LocaleTaskViewHolder(val binding: ItemHistoryUndeterminedListBinding) :
        ComViewHolder(binding.root)

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentToDoHistoryBinding.inflate(inflater, parent, false)
}