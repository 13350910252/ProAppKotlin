package com.yinp.tools.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/**
 * @author   :yinpeng
 * date      :2021/10/13
 * package   :com.yinp.tools.adapter
 * describe  :
 */
abstract class CommonAdapter<T>(val mContext: Context, private val mDataList: List<T>) :
    RecyclerView.Adapter<SingleViewHolder>() {
    val mInflater = LayoutInflater.from(mContext)
    protected var mItemLayoutId = 0

    private var onItemClickListener: ComViewHolder.OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: ComViewHolder.OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val viewHolder = setComViewHolder(null, viewType, parent)
        viewHolder.onItemClickListener(onItemClickListener)
        return viewHolder
    }

    abstract fun setComViewHolder(view: View?, viewType: Int, parent: ViewGroup?): SingleViewHolder

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        onBindItem(holder, position, mDataList[position])
    }

    fun onBindHeader(holder: SingleViewHolder) {}


    open fun onBindItem(holder: SingleViewHolder, position: Int, item: T) {}


    override fun getItemCount() = mDataList.size
}