package com.yinp.proappkotlin.study.wanAndroid.model

import RetrofitUtil
import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.study.wanAndroid.data.RankListBean
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.proappkotlin.web.wanDisposeNetOuter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2022/1/11
 * package   :com.yinp.proappkotlin.study.wanAndroid.model
 * describe  :
 */
class WanRankModel : BaseViewModel() {
    private val _rankListBean =
        MutableStateFlow<WanResultDispose<RankListBean>>(WanResultDispose.LS())
    val rankListBean: StateFlow<WanResultDispose<RankListBean>> = _rankListBean

    /**
     * 获取积分排行榜
     */
    fun getIntegralRankList(page: Int) {
        viewModelScope.launch {
            _rankListBean.value = WanResultDispose.Start()
            wanDisposeNetOuter(_rankListBean) {
                RetrofitUtil.wandroidApiService.getRankList(page)
            }
        }
    }

}