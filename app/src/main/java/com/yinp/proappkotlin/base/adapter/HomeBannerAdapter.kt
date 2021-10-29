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
class HomeBannerAdapter(mDatas: List<HomeBannerData>, private var context: Context) :
    BannerAdapter<HomeBannerData, HomeBannerAdapter.HomeBannerViewHolder>(mDatas) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): HomeBannerViewHolder {
        val bannerBinding: ItemHomeBannerBinding =
            ItemHomeBannerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return HomeBannerViewHolder(bannerBinding)
    }

    override fun onBindView(
        holder: HomeBannerViewHolder,
        data: HomeBannerData?,
        position: Int,
        size: Int
    ) {
        val entity: HomeBannerData = mDatas[position]
        holder.bind.ivBanner.let {
            Glide.with(it).load(entity.imagePath).into(it)
        }
    }

    class HomeBannerViewHolder(bannerBinding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(bannerBinding.root) {
        var bind: ItemHomeBannerBinding = bannerBinding
    }
}