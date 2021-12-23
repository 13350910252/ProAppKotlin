package com.yinp.proappkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.DialogTipBinding
import com.yinp.proappkotlin.databinding.FragmentToDoCurBinding
import com.yinp.proappkotlin.databinding.ItemTodayUndeterminedListBinding
import com.yinp.proappkotlin.home.activity.AddUndeterminedActivity
import com.yinp.proappkotlin.room.DatabaseManager
import com.yinp.proappkotlin.room.bean.LocaleTaskBean
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import com.yinp.tools.fragment_dialog.BaseDialogFragment
import com.yinp.tools.fragment_dialog.CommonDialogFragment
import com.yinp.tools.fragment_dialog.DialogFragmentHolder
import com.yinp.tools.fragment_dialog.ViewConvertListener

/**
 * @author   :yinpeng
 * date      :2021/10/12
 * package   :com.yinp.proappkotlin
 * describe  :今日待做
 *  这种viewPager2+fragment不会触发onHiddenChanged和setUserVisibleHint
 * 会触发onResume和onPause
 */
const val UPDATE_TASK = "update_task"
const val DELETE_TASK = "delete_task"

class ToDoCurFragment : BaseFragment<FragmentToDoCurBinding>() {
    private lateinit var adapter: CommonAdapter<LocaleTaskBean>
    private val dataList = mutableListOf<LocaleTaskBean>()

    /**
     * 初始化，以可能回传递数据
     */
    companion object {
        fun getInstance() = ToDoCurFragment().apply {
            arguments = Bundle()
        }
    }

    override fun initViews() {
        initClick(this, bd.noData, bd.noTips)
        bd.noTips.text = "暂无待做任务，点击添加"
        initRecycler()
        getDataList()
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            bd.noData,
            bd.noTips -> {
                addUndeterminedActivity.launch(null)
            }
        }
    }

    /**
     * 界面跳转
     */
    private val addUndeterminedActivity =
        registerForActivityResult(object : ActivityResultContract<String, Intent>() {
            override fun createIntent(context: Context, input: String?): Intent {
                return Intent(context, AddUndeterminedActivity::class.java)
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Intent? {
                return if (resultCode == RESULT_CODE) intent
                else null
            }

        }) {
            page = 1
            getDataList()
        }

    private fun initRecycler() {
        adapter = object : CommonAdapter<LocaleTaskBean>(requireContext(), dataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): ComViewHolder {
                return LocaleTaskViewHolder(
                    ItemTodayUndeterminedListBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                )
            }

            override fun onBindItem(
                holder: RecyclerView.ViewHolder?,
                position: Int,
                item: LocaleTaskBean
            ) {
                (holder as? LocaleTaskViewHolder)?.binding?.run {
                    if (item.title.isNotEmpty()) {
                        tvTitle.text = item.title
                        tvTitle.visibility = View.VISIBLE
                    }
                    tvContent.text = item.content
                    stvFinish.setOnClickListener {
                        saveDialog(
                            "确定完成当前任务吗？",
                            holder.absoluteAdapterPosition,
                            UPDATE_TASK
                        )
                    }
                    tvDelete.setOnClickListener {
                        saveDialog(
                            "确定删除当前任务吗？",
                            holder.absoluteAdapterPosition,
                            DELETE_TASK
                        )
                    }
                }
            }

        }
        bd.rvList.layoutManager = LinearLayoutManager(context)
        //添加了这个，如果从最后开始删除就没有问题，如果随意删除就会崩溃
        //adapter.setHasStableIds(true);
        bd.rvList.adapter = adapter
        bd.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mLinearLayoutManager =
                    mLinearLayoutManager ?: recyclerView.layoutManager as? LinearLayoutManager
                mLinearLayoutManager?.let {
                    mTotalCount = it.itemCount
                    mVisiblePosition = it.findLastVisibleItemPosition()
                    (mTotalCount == 0).or(mVisiblePosition == 0).not().let {
                        if (mCanGet.and(dy > 0).and(mTotalCount.rem(mVisiblePosition) == 2)) {
                            mCanGet = false
                            page++
                            getDataList()
                        } else if (mTotalCount.rem(mVisiblePosition) != 2) {
                            mCanGet = true
                        }
                    }
                }
            }
        })
    }

    private var mTotalCount = -1
    private var mVisiblePosition = -1
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mCanGet = false

    private var page = 1

    /**
     * 供外部调用的方法来刷新
     */
    fun initDataList() {
        page = 1
        getDataList()
    }

    /**
     * 获取今日数据列表
     */
    private fun getDataList() {
        var list: ArrayList<LocaleTaskBean>? = null
        DatabaseManager.db.localeTaskDao().queryCurDateNoFinishList(0).let {
//            runBlocking {
            it to list
//            }
        }
        /**
         * 测试这样行不
         */
        var aa = DatabaseManager.db.localeTaskDao().queryCurDateNoFinishList(0).run {
            this to list
        }
        list?.let {
            if (it.size > 0) {
                if (page == 1) {
                    dataList.clear()
                }
                dataList.addAll(list)
                adapter.notifyItemRangeInserted(dataList.size, dataList.size.plus(list.size))
                bd.noLl.visibility = View.GONE
                bd.rvList.visibility = View.VISIBLE
            }
        } ?: let {
            if (dataList.size == 0) {
                bd.noLl.visibility = View.VISIBLE
                bd.rvList.visibility = View.GONE
            }
        }
    }

    /**
     * 点击完成和删除出现的问题
     *
     * @param position
     * @param type
     */
    private fun initType(position: Int, type: String) {
        val bean: LocaleTaskBean = dataList[position]
//        when (type) {
//            UPDATE_TASK -> {
//                bean.isFinish = 1
//                dealLocaleTask.update(bean)
//                dataList.removeAt(position)
//                adapter.notifyItemRemoved(position)
//                if (dataList.size <= 0) {
//                    bd.noLl.visibility = View.VISIBLE
//                    bd.rvList.visibility = View.GONE
//                }
//            }
//            DELETE_TASK -> {
//                dealLocaleTask.delete(bean)
//                dataList.removeAt(position)
//                adapter.notifyItemRemoved(position)
//                if (dataList.size <= 0) {
//                    bd.noLl.visibility = View.VISIBLE
//                    bd.rvList.visibility = View.GONE
//                }
//            }
//        }
    }

    private fun saveDialog(title: String, position: Int, type: String) {
        CommonDialogFragment.newInstance(context).setLayout(
            DialogTipBinding.inflate(
                LayoutInflater.from(
                    context
                ), null, false
            )
        )
            .setViewConvertListener(object : ViewConvertListener() {
                override fun convertView(
                    holder: DialogFragmentHolder,
                    dialogFragment: BaseDialogFragment,
                    viewBinding: ViewBinding
                ) {
                    (viewBinding as? DialogTipBinding)?.apply {
                        tvTitle.text = title
                        tvLeft.setOnClickListener { dialogFragment.dismiss() }
                        tvRight.setOnClickListener {
                            dialogFragment.dismiss()
                            initType(position, type)
                        }
                    }
                }
            }).setAnimStyle(BaseDialogFragment.CENTER).setGravity(BaseDialogFragment.CENTER)
            .setPercentSize(0.9f, 0f).show(
                childFragmentManager
            )
    }

    internal class LocaleTaskViewHolder(val binding: ItemTodayUndeterminedListBinding) :
        ComViewHolder(binding.root)

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentToDoCurBinding.inflate(inflater, parent, false)
}