package com.yinp.proappkotlin.major.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yinp.proappkotlin.base.BaseFragment
import com.yinp.proappkotlin.base.bean.BaseBean
import com.yinp.proappkotlin.databinding.FragmentRecreationBinding
import com.yinp.proappkotlin.databinding.ItemRecreationListBinding
import com.yinp.tools.adapter.ComViewHolder
import com.yinp.tools.adapter.CommonAdapter
import com.yinp.tools.adapter.SingleViewHolder

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.major
 * describe  :
 */
class RecreationFragment : BaseFragment<FragmentRecreationBinding>() {
    private lateinit var adapter: CommonAdapter<BaseBean>
    private val dataList = mutableListOf<BaseBean>()

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
            ): SingleViewHolder {
                val viewHolder = SingleViewHolder(
                    ItemRecreationListBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                )
                val binding = viewHolder.binding as ItemRecreationListBinding
                binding.stvStart.setOnClickListener {
                    val url = dataList[viewHolder.absoluteAdapterPosition].url
                    if (!url.isNullOrEmpty()) {
                        gotoActivity(url)
                    }
                }
                return viewHolder
            }

            override fun onBindItem(holder: SingleViewHolder, position: Int, item: BaseBean) {
                (holder.binding as ItemRecreationListBinding).apply {
                    tvTitle.text = item.title
                    tvIntroduce.text = item.content
                }
            }
        }
        bd.rvList.layoutManager = LinearLayoutManager(context)
        bd.rvList.setHasFixedSize(true)
        bd.rvList.adapter = adapter
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentRecreationBinding.inflate(inflater, parent, false)

}