package com.yinp.proappkotlin.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yinp.proappkotlin.databinding.ItemHomeBannerBinding
import com.yinp.proappkotlin.home.bean.HomeBannerData
import com.youth.banner.adapter.BannerAdapter

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.base.adapter
 * describe  :
 */
class HomeBannerAdapter(private var mData: List<HomeBannerData>, private val mContext: Context) :
    BannerAdapter<HomeBannerData, HomeBannerAdapter.HomeBannerViewHolder>(mData) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): HomeBannerViewHolder {
        return HomeBannerViewHolder(
            ItemHomeBannerBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
        )
    }

    override fun onBindView(
        holder: HomeBannerViewHolder,
        data: HomeBannerData?,
        position: Int,
        size: Int
    ) {
        val entity: HomeBannerData = mData[position]
        holder.bind.ivBanner.let {
            Glide.with(mContext).load(entity.imagePath).into(it)
        }
    }

    class HomeBannerViewHolder(val bind: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(bind.root)
}