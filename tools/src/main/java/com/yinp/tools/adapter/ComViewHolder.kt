package com.yinp.tools.adapter

import android.view.View
import android.view.View.OnLongClickListener
import androidx.recyclerview.widget.RecyclerView

/**
 * @author   :yinpeng
 * date      :2021/10/14
 * package   :com.yinp.tools.adapter
 * describe  :
 */
abstract class ComViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener, OnLongClickListener {
    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    private var mOnItemClickListener: OnItemClickListener? = null
    override fun onClick(v: View?) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener!!.onItemClick(layoutPosition, v)
        }
    }

    fun onItemClickListener(listener: OnItemClickListener?) {
        mOnItemClickListener = listener
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View?)
    }


}