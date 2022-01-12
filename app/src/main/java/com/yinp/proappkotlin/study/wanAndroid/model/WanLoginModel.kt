package com.yinp.proappkotlin.study.wanAndroid.model

import RetrofitUtil
import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.study.wanAndroid.data.WanLoginBean
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
class WanLoginModel : BaseViewModel() {
    private val _wanLoginBean =
        MutableStateFlow<WanResultDispose<WanLoginBean>>(WanResultDispose.LS())
    val wanLoginBean: StateFlow<WanResultDispose<WanLoginBean>> = _wanLoginBean

    /**
     * 登录WanAndroid
     */
    fun login(userName: String, password: String) {
        viewModelScope.launch {
            _wanLoginBean.value = WanResultDispose.Start()
            wanDisposeNetOuter(_wanLoginBean) {
                RetrofitUtil.wandroidApiService.login(userName, password)
            }
        }
    }
}