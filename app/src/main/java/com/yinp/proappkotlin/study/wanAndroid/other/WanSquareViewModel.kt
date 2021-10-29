package com.yinp.proappkotlin.study.wanAndroid.other

import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.WanAndroid
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.study.wanAndroid.data.WanSquareListData
import com.yinp.proappkotlin.web.data.BaseRespData
import com.yinp.proappkotlin.web.data.WanAndroidData
import com.yinp.proappkotlin.web.disposeNetOuter
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
    private val _wanSquareListData = MutableStateFlow(BaseRespData<WanSquareListData>())
    val wanSquareListData: StateFlow<BaseRespData<WanSquareListData>> = _wanSquareListData
    val channel = Channel<WanAndroidData<*>>()

    fun getSquareList(page: Int) {
        viewModelScope.launch {
            disposeNetOuter(_wanSquareListData, channel, WanAndroid.NET_TYPE_A) {
                RetrofitUtil.wandroidApiService.getSquareList(page)
            }
        }
    }
}