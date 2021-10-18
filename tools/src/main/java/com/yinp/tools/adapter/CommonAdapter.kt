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
abstract class CommonAdapter<T>() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected lateinit var mInflater: LayoutInflater
    protected lateinit var mContext: Context
    protected lateinit var datalist: List<T>
    protected var mItemLayoutId = 0
    protected var headerlayoutid = 0
    val RECYLERITEMTYPE = 0
    val RECYLERHEADTYPE = 1

    private var onItemClickListener: ComViewHolder.OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: ComViewHolder.OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    constructor(context: Context, datalist: List<T>) : this(context, datalist, -1, 0)

    constructor(context: Context, datalist: List<T>, layoutid: Int) : this(
        context,
        datalist,
        layoutid,
        0
    )

    constructor(context: Context, datalist: List<T>, layoutid: Int, headerlayoutid: Int)
            : this(context, datalist, layoutid, headerlayoutid, false)

    constructor(
        context: Context, datalist: List<T>,
        layoutid: Int,
        headerlayoutid: Int,
        isneedM: Boolean
    ) : this() {
        mContext = context
        this.datalist = datalist
        mItemLayoutId = layoutid
        this.headerlayoutid = headerlayoutid
        mInflater = LayoutInflater.from(context)
    }

    override fun getItemViewType(position: Int): Int {
        return if (headerlayoutid != 0 && position == 0) {
            RECYLERHEADTYPE
        } else {
            RECYLERITEMTYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: ComViewHolder?
        if (mItemLayoutId == -1) {
            viewHolder = setComViewHolder(null, viewType, parent)
            viewHolder!!.onItemClickListener(onItemClickListener)
            return viewHolder
        }
        if (headerlayoutid != 0 && viewType == RECYLERHEADTYPE) {
            val view = mInflater.inflate(headerlayoutid, parent, false)
            viewHolder = setComViewHolder(view, viewType, null)
        } else {
            val view = mInflater.inflate(mItemLayoutId, parent, false)
            viewHolder = setComViewHolder(view, viewType, null)
            viewHolder!!.onItemClickListener(onItemClickListener)
        }

        return viewHolder!!
    }

    abstract fun setComViewHolder(view: View?, viewType: Int, parent: ViewGroup?): ComViewHolder?

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (headerlayoutid != 0) {
            if (position == 0) {
                onBindHeader(holder)
            } else {
                onBindItem(holder, position - 1, datalist[position - 1])
            }
        } else {
            onBindItem(holder, position, datalist[position])
        }
    }

    fun onBindHeader(holder: RecyclerView.ViewHolder?) {}


    open fun onBindItem(holder: RecyclerView.ViewHolder?, position: Int, item: T) {}


    override fun getItemCount(): Int {
        return if (headerlayoutid != 0) {
            datalist.size + 1
        } else {
            datalist.size
        }
    }
}