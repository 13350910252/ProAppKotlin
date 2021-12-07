package com.yinp.proappkotlin.study.wanAndroid.model

import RetrofitUtil
import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.study.wanAndroid.data.WanSysListData
import com.yinp.proappkotlin.web.data.BaseRespData
import com.yinp.proappkotlin.web.data.WanAndroidCall
import com.yinp.proappkotlin.web.disposeNetOuter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2021/10/28
 * package   :com.yinp.proappkotlin.study.wanAndroid.other
 * describe  :
 */
class WanSystemModel : BaseViewModel() {
    private val _wanSysListData = MutableStateFlow(BaseRespData<ArrayList<WanSysListData>>())
    val wanSysListData: StateFlow<BaseRespData<ArrayList<WanSysListData>>> = _wanSysListData

    fun getSystemInfo(call: WanAndroidCall) {
        viewModelScope.launch {
            disposeNetOuter(_wanSysListData, call) {
                RetrofitUtil.wandroidApiService.getSystemList()
            }
        }
    }
}