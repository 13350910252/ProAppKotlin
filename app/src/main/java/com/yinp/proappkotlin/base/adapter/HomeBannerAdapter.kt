package com.yinp.proappkotlin.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yinp.proappkotlin.base.bean.BannerEntity
import com.yinp.proappkotlin.databinding.ItemHomeBannerBinding
import com.yinp.proappkotlin.utils.GlideUtils
import com.youth.banner.adapter.BannerAdapter

/**
 * @author   :yinpeng
 * date      :2021/10/11
 * package   :com.yinp.proappkotlin.base.adapter
 * describe  :
 */
class HomeBannerAdapter(val datas: List<BannerEntity?>, private var context: Context) :
    BannerAdapter<BannerEntity, HomeBannerAdapter.HomeBannerViewHolder>(datas) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): HomeBannerViewHolder {
        val bannerBinding: ItemHomeBannerBinding =
            ItemHomeBannerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return HomeBannerViewHolder(bannerBinding)
    }

    override fun onBindView(
        holder: HomeBannerViewHolder?,
        data: BannerEntity?,
        position: Int,
        size: Int
    ) {
        val entity: BannerEntity = mDatas.get(position)
        holder?.bind?.ivBanner?.let {
            GlideUtils.loadUrl(
                context,
                it,
                null,
                entity.imagePath,
                null,
                null,
                false,
                true
            )
        }
    }

    class HomeBannerViewHolder(bannerBinding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(bannerBinding.root) {
        var bind: ItemHomeBannerBinding = bannerBinding
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}