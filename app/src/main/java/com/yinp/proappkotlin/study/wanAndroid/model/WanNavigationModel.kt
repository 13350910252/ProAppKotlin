package com.yinp.proappkotlin.study.wanAndroid.model

import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.study.wanAndroid.data.NavigationListData
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.proappkotlin.web.wanDisposeNetOuter
import com.zhmyzl.mykotlin.network.RetrofitUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2021/10/28
 * package   :com.yinp.proappkotlin.study.wanAndroid.other
 * describe  :
 */
class WanNavigationModel : BaseViewModel() {
    private val _navigationListData =
        MutableStateFlow<WanResultDispose<ArrayList<NavigationListData>>>(WanResultDispose.LS())
    val navigationListData: StateFlow<WanResultDispose<ArrayList<NavigationListData>>> =
        _navigationListData


    fun getNavigationList() {
        viewModelScope.launch {
            _navigationListData.value = WanResultDispose.Start()
            wanDisposeNetOuter(_navigationListData) {
                RetrofitUtil.wandroidApiService.getNavigationList()
            }
        }
    }
}