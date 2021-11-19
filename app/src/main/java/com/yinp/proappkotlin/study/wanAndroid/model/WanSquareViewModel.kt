package com.yinp.proappkotlin.study.wanAndroid.model

import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.WanAndroid
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.study.wanAndroid.data.WanProjectListData
import com.yinp.proappkotlin.study.wanAndroid.data.WanSquareListData
import com.yinp.proappkotlin.web.data.BaseRespData
import com.yinp.proappkotlin.web.data.WanAndroidCall
import com.yinp.proappkotlin.web.data.WanAndroidData
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.proappkotlin.web.disposeNetOuter
import com.yinp.proappkotlin.web.wanDisposeNetOuter
import com.zhmyzl.mykotlin.network.RetrofitUtil
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2021/10/28
 * package   :com.yinp.proappkotlin.study.wanAndroid.other
 * describe  :
 */
class WanSquareViewModel : BaseViewModel() {

    private val _wanSquareListData =
        MutableStateFlow<WanResultDispose<WanSquareListData>>(WanResultDispose.LS())
    val wanSquareListData: StateFlow<WanResultDispose<WanSquareListData>> =
        _wanSquareListData

    fun getSquareList(page: Int) {
        viewModelScope.launch {
            _wanSquareListData.value = WanResultDispose.Start()
            wanDisposeNetOuter(_wanSquareListData) {
                RetrofitUtil.wandroidApiService.getSquareList(page)
            }
        }
    }
}