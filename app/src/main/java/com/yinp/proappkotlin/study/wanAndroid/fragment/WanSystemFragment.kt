package com.yinp.proappkotlin.study.wanAndroid.fragment

import android.os.Bundle
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
import com.yinp.proappkotlin.databinding.FragmentWanSystemBinding
import com.yinp.proappkotlin.databinding.ItemSystemOneBinding
import com.yinp.proappkotlin.databinding.ItemSystemTwoBinding
import com.yinp.proappkotlin.study.wanAndroid.WanSysActivity
import com.yinp.proappkotlin.study.wanAndroid.data.WanSysListData
import com.yinp.proappkotlin.study.wanAndroid.model.WanSystemModel
import com.yinp.proappkotlin.web.data.WanAndroidCall
import com.yinp.proappkotlin.web.data.WanAndroidData
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
class WanSystemFragment : BaseFragment<FragmentWanSystemBinding>() {

    private lateinit var adapter: CommonAdapter<WanSysListData>
    private val mDataList = mutableListOf<WanSysListData>()

    private val viewModel by lazy {
        ViewModelProvider(this)[WanSystemModel::class.java]
    }

    companion object {
        fun getInstance() = WanSystemFragment().apply {
            arguments = Bundle()
        }
    }

    override fun initViews() {
        initRecycler()
        getSystemInfo()
    }

    private fun initRecycler() {
        bd.bottom.noLl.visibility = View.GONE
        adapter = object : CommonAdapter<WanSysListData>(requireContext(), mDataList) {
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
                return if (mDataList[position].isTitle) {
                    0
                } else {
                    1
                }
            }

            override fun onBindItem(holder: SingleViewHolder, position: Int, item: WanSysListData) {
                holder.binding.let {
                    when (it) {
                        is ItemSystemOneBinding -> it.run {
                            tvTitle.text = item.name
                        }
                        is ItemSystemTwoBinding -> it.run {
                            stvValue.text = item.name
                            stvValue.setOnClickListener {
                                gotoActivity<WanSysActivity>()
                            }
                        }
                    }
                }
            }
        }
        bd.rvList.layoutManager = FlexboxLayoutManager(context).apply {
            //flexDirection ?????????????????????????????????????????????????????????????????? LinearLayout ??? vertical ??? horizontal???
            flexDirection = FlexDirection.ROW //??????????????????????????????????????????
            //flexWrap ??????????????? Flex ??? LinearLayout ?????????????????????????????????????????????flexWrap?????????????????????????????????
            flexWrap = FlexWrap.WRAP //?????????????????????
            //justifyContent ???????????????????????????????????????????????????
            justifyContent = JustifyContent.FLEX_START //???????????????????????????
        }
        bd.rvList.adapter = adapter
    }

    private fun getSystemInfo() {
        viewModel.getSystemInfo(object : WanAndroidCall() {
            override fun onStart() {
                showLoading("?????????...")
            }

            override fun onSuccess() {
                hideLoading()
            }
        })
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wanSysListData.collect {
                    if (it is WanAndroidData) {
                        val listBeans = it.data
                        if (listBeans.isNullOrEmpty().not()) {
                            for (item in listBeans!!) {
                                mDataList.add(
                                    WanSysListData(
                                        item.courseId,
                                        item.id,
                                        item.name,
                                        item.order,
                                        item.parentChapterId,
                                        item.userControlSetTop,
                                        item.visible,
                                        true
                                    )
                                )
                                item.children?.let {
                                    for (item2 in item.children) {
                                        mDataList.add(
                                            WanSysListData(
                                                item2.courseId,
                                                item2.id,
                                                item2.name,
                                                item2.order,
                                                item2.parentChapterId,
                                                item2.userControlSetTop,
                                                item2.visible,
                                                false
                                            )
                                        )
                                    }
                                }
                            }
                            bd.rvList.visibility = View.VISIBLE
                            bd.bottom.noLl.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        } else {
                            bd.rvList.visibility = View.GONE
                            bd.bottom.noLl.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun getBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentWanSystemBinding.inflate(inflater, parent, false)
}