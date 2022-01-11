package com.yinp.proappkotlin.study.wanAndroid.model

import RetrofitUtil
import androidx.lifecycle.viewModelScope
import com.yinp.proappkotlin.base.BaseViewModel
import com.yinp.proappkotlin.study.wanAndroid.data.IntegralBean
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
class WanMeModel : BaseViewModel() {
    private val _integralBean =
        MutableStateFlow<WanResultDispose<IntegralBean>>(WanResultDispose.LS())
    val integralBean: StateFlow<WanResultDispose<IntegralBean>> = _integralBean

    /**
     * 获取积分
     */
    fun getIntegralInfo() {
        viewModelScope.launch {
            wanDisposeNetOuter(_integralBean) {
                RetrofitUtil.wandroidApiService.getIntegral()
            }
        }
    }

    /**
     * 退出WanAndroid登录
     */
    private val _loginOut =
        MutableStateFlow<WanResultDispose<String>>(WanResultDispose.LS())
    val loginOut: StateFlow<WanResultDispose<String>> = _loginOut
    fun loginOut() {
        viewModelScope.launch {
            wanDisposeNetOuter(_loginOut) {
                RetrofitUtil.wandroidApiService.loginOut()
            }
        }
    }
}