package com.yinp.proappkotlin.study.wanAndroid.model

import RetrofitUtil
import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.study.wanAndroid.data.CollectionListBean
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
class WanCollectModel : BaseViewModel() {
    private val _collectionListBean =
        MutableStateFlow<WanResultDispose<CollectionListBean>>(WanResultDispose.LS())
    val collectionListBean: StateFlow<WanResultDispose<CollectionListBean>> = _collectionListBean

    /**
     * 获取收藏列表
     */
    fun getCollectList(page: Int) {
        viewModelScope.launch {
            _collectionListBean.value = WanResultDispose.Start()
            wanDisposeNetOuter(_collectionListBean) {
                RetrofitUtil.wandroidApiService.getCollectList(page)
            }
        }
    }
}