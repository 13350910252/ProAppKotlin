package com.yinp.proappkotlin.major.fragment

import android.content.Context
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
import com.yinp.proappkotlin.dpToPx
import com.yinp.proappkotlin.mtools.ToolsTopBean
import com.yinp.proappkotlin.utils.AppUtils
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import com.yinp.tools.adapter.SingleViewHolder

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
            ): SingleViewHolder {
                return SingleViewHolder(
                    ItemToolsTopListBinding.inflate(
                        mInflater,
                        parent,
                        false
                    )
                )
            }

            override fun onBindItem(holder: SingleViewHolder, position: Int, item: ToolsTopBean) {
                (holder.binding as ItemToolsTopListBinding).apply {
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
                if (topList[position].url.isNotEmpty()) {
                    gotoActivity(topList[position].url)
                }
            }
        })
        bd.rvList.let {
            it.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            it.addItemDecoration(SpaceItemDecoration(requireContext(), 3))
            it.adapter = topAdapter
        }
    }

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
                    outRect.left = 20.dpToPx()
                    outRect.right = 10.dpToPx()
                }
                1 -> {
                    outRect.left = 10.dpToPx()
                    outRect.right = 10.dpToPx()
                }
                else -> {
                    outRect.left = 10.dpToPx()
                    outRect.right = 20.dpToPx()
                }
            }
        }
    }
}