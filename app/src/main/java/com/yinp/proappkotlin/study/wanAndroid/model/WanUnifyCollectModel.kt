package com.yinp.proappkotlin.study.wanAndroid.model

import RetrofitUtil
import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.base.BaseViewModel
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
class WanUnifyCollectModel : BaseViewModel() {
    private val _unifyCancelCollect =
        MutableStateFlow<WanResultDispose<String>>(WanResultDispose.LS())
    val unifyCancelCollect: StateFlow<WanResultDispose<String>> = _unifyCancelCollect

    /**
     * 取消收藏
     */
    fun cancelCollect(id: Int) {
        viewModelScope.launch {
            wanDisposeNetOuter(_unifyCancelCollect) {
                RetrofitUtil.wandroidApiService.cancelCollect(id)
            }
        }
    }

    /**
     * 增加收藏
     */
    private val _unifyAddCollect =
        MutableStateFlow<WanResultDispose<String>>(WanResultDispose.LS())
    val unifyAddCollect: StateFlow<WanResultDispose<String>> = _unifyAddCollect
    fun addCollect(id: Int) {
        viewModelScope.launch {
            wanDisposeNetOuter(_unifyAddCollect) {
                RetrofitUtil.wandroidApiService.addCollect(id)
            }
        }
    }
}