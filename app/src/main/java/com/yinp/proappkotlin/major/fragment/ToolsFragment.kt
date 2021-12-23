package com.yinp.proappkotlin.major.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.yinp.proappkotlin.R
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.databinding.FragmentToolsBinding
import com.yinp.proappkotlin.databinding.ItemToolsTopListBinding
import com.yinp.proappkotlin.mtools.ToolsTopBean
import com.yinp.proappkotlin.utils.AppUtils
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class ToolsFragment : BaseFragment<FragmentToolsBinding>() {
    override fun initViews() {
        initRecycler()
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentToolsBinding.inflate(inflater, parent, false)

    private val topList = mutableListOf<ToolsTopBean>()
    private var topAdapter: CommonAdapter<ToolsTopBean>? = null

    private fun initRecycler() {
        topList.add(
            ToolsTopBean(
                "百度地图",
                R.mipmap.map,
                "com.yinp.proapp.module.baidu_map.activity.BaiduMapActivity"
            )
        )
        topList.add(ToolsTopBean("计算器", R.mipmap.calculator, ""))
        topList.add(
            ToolsTopBean(
                "日历待办",
                R.mipmap.calendar,
                "com.yinp.proapp.module.calendar.activity.CalendarAddActivity"
            )
        )
        topList.add(
            ToolsTopBean(
                "QQ",
                R.mipmap.qq,
                "com.yinp.proapp.module.qq.activity.QQLoginActivity"
            )
        )
        topAdapter = object : CommonAdapter<ToolsTopBean>(requireContext(), topList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): ComViewHolder {
                return ToolsTopViewHolder(
                    ItemToolsTopListBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                )
            }

            override fun onBindItem(
                holder: RecyclerView.ViewHolder?,
                position: Int,
                item: ToolsTopBean
            ) {
                val viewHolder = holder as ToolsTopViewHolder
                viewHolder.binding.run {
                    if (item.drawableId == -1) {
                        ivImg.setBackgroundResource(R.mipmap.default1)
                    } else {
                        ivImg.setBackgroundResource(item.drawableId)
                    }
                    tvTitle.text = item.title
                }
            }
        }
        topAdapter?.setOnItemClickListener(object : ComViewHolder.OnItemClickListener {
            override fun onItemClick(position: Int, view: View?) {
                if (topList[position].url.isNullOrEmpty().not()) {
                    goToActivity(Intent().apply {
                        setClassName(requireContext(), topList[position].url!!)
                    })
                }
            }
        })
        bd.rvList.let {
            it.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            it.addItemDecoration(SpaceItemDecoration(requireContext(), 3))
            it.adapter = topAdapter
        }
    }

    internal class ToolsTopViewHolder(val binding: ItemToolsTopListBinding) :
        ComViewHolder(binding.root)

    class SpaceItemDecoration(
        private val context: Context, //位移间距
        private val space: Int
    ) : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            when (parent.getChildAdapterPosition(view) % space) {
                0 -> {
                    outRect.left = AppUtils.dpToPx(context, 20.0f)
                    outRect.right = AppUtils.dpToPx(context, 10.0f)
                }
                1 -> {
                    outRect.left = AppUtils.dpToPx(context, 10.0f)
                    outRect.right = AppUtils.dpToPx(context, 10.0f)
                }
                else -> {
                    outRect.left = AppUtils.dpToPx(context, 10.0f)
                    outRect.right = AppUtils.dpToPx(context, 20.0f)
                }
            }
        }
    }
}