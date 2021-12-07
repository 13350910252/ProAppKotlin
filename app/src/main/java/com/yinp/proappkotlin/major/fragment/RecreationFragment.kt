package com.yinp.proappkotlin.major.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.base.bean.BaseBean
import com.yinp.proappkotlin.databinding.FragmentRecreationBinding
import com.yinp.proappkotlin.databinding.ItemRecreationListBinding
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import java.util.*

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class RecreationFragment : BaseFragment<FragmentRecreationBinding>() {
    private var adapter: CommonAdapter<BaseBean>? = null
    private val dataList: MutableList<BaseBean> = ArrayList<BaseBean>()

    override fun initViews() {
        initRecycler()
    }

    private fun initRecycler() {
        dataList.add(
            BaseBean(
                "贪吃蛇", "com.yinp.proapp.module.game.tanchishe.GSGameContentActivity", null,
                "一个自己写的利用canvas画出贪吃蛇的游戏，目前未完成"
            )
        )
        dataList.add(
            BaseBean(
                "四色地图游戏", "com.yinp.proapp.module.game.fourcolormap.FourColorMapActivity", null,
                "一个自己通过svg地图实现的四色地图游戏"
            )
        )
        adapter = object : CommonAdapter<BaseBean>(requireContext(), dataList) {
            override fun setComViewHolder(
                view: View?,
                viewType: Int,
                parent: ViewGroup?
            ): ComViewHolder {
                val viewHolder = ViewHolder(
                    ItemRecreationListBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                )
                viewHolder.binding.stvStart.setOnClickListener {
                    val url: String? = dataList[viewHolder.absoluteAdapterPosition].url
                    if (!url.isNullOrEmpty()) {
                        val intent = Intent().apply {
                            setClassName(mContext, url)
                        }
                        goToActivity(intent)
                    }
                }
                return viewHolder
            }

            override fun onBindItem(
                holder: RecyclerView.ViewHolder?,
                position: Int,
                item: BaseBean
            ) {
                val viewHolder = holder as ViewHolder
                viewHolder.binding.tvTitle.text = item.title
                viewHolder.binding.tvIntroduce.text = item.content
            }
        }
        bd.rvList.layoutManager = LinearLayoutManager(context)
        bd.rvList.setHasFixedSize(true)
        bd.rvList.adapter = adapter
    }

    internal class ViewHolder(val binding: ItemRecreationListBinding) :
        ComViewHolder(binding.root)

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentRecreationBinding.inflate(inflater, parent, false)

}