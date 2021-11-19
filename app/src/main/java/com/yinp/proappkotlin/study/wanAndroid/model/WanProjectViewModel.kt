package com.yinp.proappkotlin.study.wanAndroid.model

import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.study.wanAndroid.data.WanProjectListData
import com.yinp.proappkotlin.web.data.WanResultDispose
import com.yinp.proappkotlin.web.wanDisposeNetOuter
import com.zhmyzl.mykotlin.network.RetrofitUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author   :yinpeng
 * date      :2021/11/2
 * package   :com.yinp.proappkotlin.study.wanAndroid.model
 * describe  :
 */
class WanProjectViewModel : BaseViewModel() {

    private val _wanProjectListData =
        MutableStateFlow<WanResultDispose<WanProjectListData>>(WanResultDispose.LS())
    val wanProjectListData: StateFlow<WanResultDispose<WanProjectListData>> =
        _wanProjectListData

    fun getProjectList(page: Int) {
        viewModelScope.launch {
            _wanProjectListData.value = WanResultDispose.Start()
            wanDisposeNetOuter(_wanProjectListData) {
                RetrofitUtil.wandroidApiService.getProjectList(page)
            }
        }
    }
}